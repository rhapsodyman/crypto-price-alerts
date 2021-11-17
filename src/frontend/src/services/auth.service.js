import http from "../http-common";

class AuthService {
  login(user) {
    return http
      .post('/auth/login', {
        username: user.username,
        password: user.password
      })
      .then(response => {
        if (response.data.accessToken) {
          sessionStorage.setItem('user', JSON.stringify(response.data));
        }

        return response.data;
      });
  }

  logout() {
    sessionStorage.removeItem('user');
  }

  register(user) {
    return http.post('/auth/register', {
      username: user.username,
      phone: user.phone,
      password: user.password
    });
  }
}

export default new AuthService();