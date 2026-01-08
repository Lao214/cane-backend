# 甘蔗信息综合管理系统 (Cane Backend)

## 项目简介

甘蔗信息综合管理系统 (Cane Backend) 是一个基于Spring Boot开发的现代化后端服务，专注于甘蔗相关信息的管理、分类、问答和敏感性数据分析。系统采用微服务架构设计，提供了完整的用户认证、数据管理和API接口服务。

## 技术栈

- **核心框架**: Spring Boot 2.7.6
- **持久层框架**: MyBatis-Plus
- **安全框架**: Spring Security + JWT
- **数据库**: MySQL
- **缓存**: Redis
- **文件存储**: MinIO
- **JSON处理**: FastJSON
- **工具库**: Hutool、Apache Commons Lang3
- **模板引擎**: Velocity (用于代码生成)

## 主要功能模块

### 1. 用户管理模块
- 用户注册、登录、权限分配
- 用户信息修改、头像上传
- 用户角色管理（RBAC权限控制）

### 2. 甘蔗信息管理
- 甘蔗数据的增删改查
- 甘蔗分类管理（支持树形结构）
- 甘蔗品种信息展示

### 3. 甘蔗分类管理
- 多级分类管理
- 分类树结构展示
- 分类搜索和过滤

### 4. 甘蔗区域管理
- 种质区域信息管理
- 区域数据统计分析

### 5. 专家问答系统
- 甘蔗相关问题的提问与回答
- 问答数据管理
- 专家互动功能

### 6. 甘蔗敏感性分析
- 敏感性指标数据管理
- 抗性等级分析
- 数据统计与展示

### 7. 新闻资讯管理
- 新闻发布、编辑、展示
- 首页新闻展示
- 新闻分类管理

### 8. 联系方式管理
- 联系信息管理
- 意见反馈收集

## 系统架构

- **后端**: Spring Boot + Spring Security + MyBatis-Plus
- **数据库**: MySQL (存储业务数据)
- **缓存**: Redis (存储用户会话、临时数据)
- **文件存储**: MinIO (存储图片等静态资源)
- **认证**: JWT Token认证机制

## 环境要求

- Java 8+
- Maven 3.6+
- MySQL 5.7+
- Redis
- MinIO

## 安装与配置

### 1. 环境准备

首先确保系统已安装以下软件：

```bash
# Java 8+
java -version

# Maven 3.6+
mvn -version

# MySQL
mysql --version

# Redis
redis-server --version

# MinIO (或使用Docker)
docker --version
```

### 2. 数据库配置

在 [application.properties](file:///Users/echoes/Downloads/cane-backend-master/src/main/resources/application.properties) 文件中配置数据库连接信息：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/cane?useSSL=false&serverTimezone=GMT%2B8
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Redis配置

在 [application.properties](file:///Users/echoes/Downloads/cane-backend-master/src/main/resources/application.properties) 文件中配置Redis连接信息：

```properties
spring.redis.host = 127.0.0.1
spring.redis.port = 6379
spring.redis.database = 2
spring.redis.timeout = 180000
```

### 4. MinIO配置

在 [application.properties](file:///Users/echoes/Downloads/cane-backend-master/src/main/resources/application.properties) 文件中配置MinIO连接信息：

```properties
minio.endpoint = http://127.0.0.1:9000
minio.accessKey = your_access_key
minio.secretKey = your_secret_key
minio.bucketName = cane
```

### 5. 构建与运行

```bash
# 克隆项目
git clone <repository_url>

# 进入项目目录
cd cane-backend

# 编译打包
mvn clean package

# 运行项目
java -jar target/caneV1.0.jar
```

项目默认运行在端口 `9914`，可通过 [application.properties](file:///Users/echoes/Downloads/cane-backend-master/src/main/resources/application.properties) 中的 `server.port` 属性修改。

## API接口

### 认证相关

- `POST /security/login` - 用户登录
- `GET /security/logout` - 用户登出

### 甘蔗数据相关

- `GET /cane/getCane/{page}/{limit}` - 分页获取甘蔗数据
- `POST /cane/addCane` - 添加甘蔗数据
- `POST /cane/updateCane` - 更新甘蔗数据
- `DELETE /cane/delCane/{id}` - 删除甘蔗数据

### 甘蔗分类相关

- `GET /category/getCategory/{page}/{limit}` - 分页获取分类数据
- `GET /category/getCategoryTree` - 获取分类树结构

### 问答系统相关

- `GET /qa/getCaneQa/{page}/{limit}` - 分页获取问答数据
- `POST /qa/addCaneQa` - 添加问答数据

### 敏感性数据相关

- `GET /caneSensitivity/getCane/{page}/{limit}` - 分页获取敏感性数据
- `POST /caneSensitivity/addCane` - 添加敏感性数据
- `GET /caneSensitivity/getFilterOptions` - 获取过滤选项

## 项目特点

1. **安全认证**: 基于JWT的无状态认证，配合Spring Security提供安全保护
2. **权限控制**: 基于角色的访问控制(RBAC)，支持细粒度权限管理
3. **缓存优化**: 使用Redis缓存提高系统性能
4. **文件管理**: 集成MinIO对象存储，用于图片等静态资源管理
5. **数据管理**: 支持批量操作，提高数据处理效率
6. **接口友好**: RESTful API设计，便于前端调用
7. **代码生成**: 集成MyBatis-Plus代码生成器

## 部署说明

### Docker部署

项目包含Dockerfile，可直接构建Docker镜像：

```bash
# 构建镜像
docker build -t cane-backend .

# 运行容器
docker run -d -p 9914:9914 --name cane-backend cane-backend
```

## 贡献者

- 苏运浩 (主要开发者)

## 许可证

本项目采用 MIT 许可证，详情请参阅 [LICENSE](file:///Users/echoes/Downloads/cane-backend-master/LICENSE) 文件。