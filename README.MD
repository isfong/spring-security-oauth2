# 使用 Spring-Boot + Spring-Security + OAuth2 + JWT 集成的项目
# 适用于自建用户系统，使用OAuth2的账号密码认证模式
###**认证会用到的相关请求**
#####注：所有请求均为post请求。
## 获取access_token请求（/oauth/token） 
######请求所需参数：client_id、client_secret、grant_type、username、password
`http://localhost/oauth/token?client_id=demoClientId&client_secret=demoClientSecret&grant_type=password&username=demoUser&password=50575tyL86xp29O380t1
`

## 检查头肯是否有效请求（/oauth/check_token） 
######请求所需参数：token
`
http://localhost/oauth/check_token?token=f57ce129-2d4d-4bd7-1111-f31ccc69d4d1
`
## 刷新token请求（/oauth/token） 
###### 请求所需参数：grant_type、refresh_token、client_id、client_secret 
###### 其中grant_type为固定值：grant_type=refresh_token
`
http://localhost/oauth/token?grant_type=refresh_token&refresh_token=fbde81ee-f419-42b1-1234-9191f1f95be9&client_id=demoClientId&client_secret=demoClientSecret
`