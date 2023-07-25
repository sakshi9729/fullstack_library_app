export const oktaConfig = {
    clientId: '0oa9vqj7v2o8NC40f5d7',
    issuer: 'https://dev-80655387.okta.com/oauth2/default',
    redirectUri: 'http://localhost:3000/login/callback',
    scopes: ['openid', 'profile', 'email'],
    pkce: true,
    disableHttpsCheck: true,
}