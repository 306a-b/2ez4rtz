import vk
login = '+79686399157'
password = 'zhiznbol'
my_app = '5123707'
vk_session = vk.AuthSession(app_id=my_app, user_login=login, user_password=password, scope=4096 + 8 + 262144)
token = vk.Session.get_access_token(vk_session)