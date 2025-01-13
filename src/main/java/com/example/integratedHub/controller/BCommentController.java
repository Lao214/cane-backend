package com.example.integratedHub.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BCarousel;
import com.example.integratedHub.entity.BComment;
import com.example.integratedHub.entity.securityVo.LoginUser;
import com.example.integratedHub.service.BCommentService;
import com.example.integratedHub.utils.JwtUtil;
import com.example.integratedHub.utils.RedisService;
import com.example.integratedHub.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 劳威锟
 * @since 2024-08-14
 */
@RestController
@RequestMapping("/comment")
public class BCommentController {

    @Autowired
    private BCommentService bCommentService;

    @Autowired
    RedisService redisService;

    @PostMapping("addComment")
    public Result addComment(@RequestBody BComment bComment, HttpServletRequest request) {
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        String username = "";
        if (token != null) {
            Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
            username = memberIdByJwtToken.get("username");
        }
        // 获取redis中用户信息
        LoginUser loginUser = redisService.getCacheObject(username);
        bComment.setUsername(username);
        bComment.setIsDeleted("正常");
        bComment.setCreateTime(new Date());
        if(bComment.getParentId().equals(0l)) {
            bComment.setCommentType("评论");
        } else  {
            bComment.setCommentType("回复");
        }
        boolean save = bCommentService.save(bComment);
        if(save) {
            bComment.setNickname(loginUser.getNickname());
            return Result.success().data("one",bComment);
        } else  {
            return Result.error();
        }

    }

//    @GetMapping("getCommentList/{resourKey}")
//    public Result getCommentList(@PathVariable String resourKey, HttpServletRequest request) {
//        // 获取评论列表
//        List<BComment> allComments = bCommentService.getCommentList(resourKey);
//
//        // 用于存储一级评论和对应的回复
//        Map<Long, List<BComment>> commentMap = new HashMap<>();
//        List<BComment> rootComments = new ArrayList<>();
//
//        // 遍历评论列表，将评论分类为一级评论和回复
//        for (BComment comment : allComments) {
//            if (comment.getRootCommentId() == 0) {
//                // 如果 rootCommentId 为 0，表示这是一级评论
//                rootComments.add(comment);
//            } else {
//                // 如果 rootCommentId 不为 0，表示这是回复，将其添加到对应一级评论的回复列表中
//                commentMap.computeIfAbsent(comment.getRootCommentId(), k -> new ArrayList<>()).add(comment);
//            }
//        }
//
//        // 将回复关联到对应的一级评论
//        for (BComment rootComment : rootComments) {
//            rootComment.setReplies(commentMap.get(rootComment.getId()));
//        }
//
//        return Result.success().data("commentList", rootComments);
//    }


    /**
     * @description
     * 这个版本的查询可以不存父级评论的nickname，而通过循环来得出父级评论的nickname
     * 但是要进行一次双重for循环，如果评论很多，就会卡爆。
     * 如果存父级评论的nickname，用户一旦修改nickname，会影响评论列表回复的准确度
     * 如果数据量小于 5000，推荐就用双层 for 循环，如果大于 5000，则使用循环+map 的方式。
     * @author echoes
     * @param[1] null
     * @throws
     * @return
     * @time 2024/8/16 15:08
     */
    @GetMapping("getCommentList/{resourKey}")
    public Result getCommentList(@PathVariable String resourKey, HttpServletRequest request) {
        // 获取评论列表
        List<BComment> allComments = bCommentService.getCommentList(resourKey);
        Integer commentCount = allComments.size();
        // 用于存储评论的 Map，方便通过 ID 快速查找父级评论
        Map<Long, BComment> commentByIdMap = new HashMap<>();
        // 用于存储一级评论和对应的回复
        Map<Long, List<BComment>> commentMap = new HashMap<>();
        List<BComment> rootComments = new ArrayList<>();

        // 遍历评论列表，将评论分类为一级评论和回复，并将评论存入 Map 中
        for (BComment comment : allComments) {
            commentByIdMap.put(comment.getId(), comment);

            if (comment.getRootCommentId() == 0) {
                // 如果 rootCommentId 为 0，表示这是一级评论
                rootComments.add(comment);
            } else {
                // 如果 rootCommentId 不为 0，表示这是回复，将其添加到对应一级评论的回复列表中
                commentMap.computeIfAbsent(comment.getRootCommentId(), k -> new ArrayList<>()).add(comment);
            }
        }

        // 将回复关联到对应的一级评论，并设置 parentNickname
        for (BComment rootComment : rootComments) {
            List<BComment> replies = commentMap.get(rootComment.getId());
            if (replies != null) {
                // 按 id 对 replies 进行排序，从旧到新（id 小到大）
                replies.sort(Comparator.comparing(BComment::getId));

                for (BComment reply : replies) {
                    // 查找并设置 parentNickname
                    BComment parentComment = commentByIdMap.get(reply.getParentId());
                    if (parentComment != null) {
                        reply.setParentNickname(parentComment.getNickname());
                    }
                }
                rootComment.setReplies(replies);
            }
        }

        return Result.success().data("commentList", rootComments).data("commentCount",commentCount);
    }


    @GetMapping("getMyReply/{page}/{limit}")
    public Result getMyReply(HttpServletRequest request,@PathVariable Long page,@PathVariable Long limit) {
        String token = request.getHeader("inhub-token");
        String username = "";
        if (token != null) {
            Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
            username = memberIdByJwtToken.get("username");
        }
//        QueryWrapper<BComment> bCommentQueryWrapper = new QueryWrapper<>();
//        bCommentQueryWrapper.select("id");
//        bCommentQueryWrapper.eq("username",username);
//        List<BComment> bComments = bCommentService.list(bCommentQueryWrapper);
//        List<String> ids = new ArrayList<>();
//        for (BComment bComment: bComments) {
//            ids.add(bComment.getId().toString());
//        }
        List<String> ids = bCommentService.getMyCommentIds(username);
        if(ids != null) {
//            QueryWrapper<BComment> bcQueryWrapper = new QueryWrapper<>();
//            bcQueryWrapper.in("parent_id",ids);
//            List<BComment> list = bCommentService.list(bcQueryWrapper);
//            if(list != null) {
//                return Result.success().data("list",list);
//            }
            //创建page对象
            Page<BComment> pageParam = new Page<>(page,limit);
            IPage<BComment> myReplyList = bCommentService.selectPage(pageParam,ids,username);
            return Result.success().data("list",myReplyList);
        }
        return Result.success();
    }


}

