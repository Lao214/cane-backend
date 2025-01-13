package com.example.integratedHub.entity.enumVo;

public enum BannerStatusEnum {
    UN_PUBLISHED(0, "未发布"),
    PUBLISHED(1, "已发布");

    private Integer code;
    private String status;

    BannerStatusEnum(Integer code, String status) {
        this.code = code;
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    // 根据时间字符串获取对应的枚举值
    public static BannerStatusEnum getByTime(String code) {
        for (BannerStatusEnum value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null; // 或者抛出异常，表示未找到对应时间
    }

    public static void main(String[] args) {
        // 通过时间字符串获取索引
        String index0800 = BannerStatusEnum.getByTime("08:00").getStatus();
        System.out.println("Index for 08:00: " + index0800);

        // 通过索引获取时间字符串
        Integer roleIdAtIndex1 = BannerStatusEnum.values()[1].getCode();
        System.out.println("Time at index 1: " + roleIdAtIndex1);
    }
}
