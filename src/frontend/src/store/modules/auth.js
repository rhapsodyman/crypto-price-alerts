
import http from "../../http-common";
import AuthService from '../../services/auth.service';
import authHeader from '../../services/auth-header';


const state = {
  user: null,
  alerts: null,
  loggedIn: false
};

const getters = {
  isAuthenticated: (state) => state.loggedIn,
  StateAlerts: (state) => state.alerts,
  StateUser: (state) => state.user,
};



const actions = {

  login({ commit }, user) {
    return AuthService.login(user).then(
      user => {
        commit('loginSuccess', user);
        return Promise.resolve(user);
      },
      error => {
        commit('loginFailure');
        return Promise.reject(error);
      }
    );
  },
  logout({ commit }) {
    AuthService.logout();
    commit('logout');
  },
  register({ commit }, user) {
    return AuthService.register(user).then(
      response => {
        commit('registerSuccess');
        return Promise.resolve(response.data);
      },
      error => {
        commit('registerFailure');
        return Promise.reject(error);
      }
    );
  },
  


  async CreateAlert({ dispatch }, alert) {
    await http.post("/priceAlerts/alerts", alert, { headers: authHeader() });
    return await dispatch("GetAlerts");
  },


  async DeleteAlert({ dispatch }, id) {
    await http.delete("/priceAlerts/alerts/" + id, { headers: authHeader() });
    return await dispatch("GetAlerts");
  },

  async GetAlerts({ commit }) {
    let response = await http.get("/priceAlerts/alerts", { headers: authHeader() });
    commit("setAlerts", response.data);
  },

};

const mutations = {

  loginSuccess(state, user) {
    state.loggedIn = true;
    state.user = user;
  },
  loginFailure(state) {
    state.loggedIn = false;
    state.user = null;
  },
  logout(state) {
    state.loggedIn = false;
    state.user = null;
  },
  registerSuccess(state) {
    state.loggedIn = false;
  },
  registerFailure(state) {
    state.loggedIn = false;
  },

  setAlerts(state, alerts) {
    state.alerts = alerts;
  }
};

export default {
  state,
  getters,
  actions,
  mutations,
};