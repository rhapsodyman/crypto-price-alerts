import Vue from "vue";
import VueRouter from "vue-router";
import store from "../store";


Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    redirect: '/login'
  },
  {
    path: "/login",
    name: "Login",
    component: () => import("../components/LoginForm"),
    meta: { guest: true }
  },
  {
    path: "/register",
    name: "Register",
    component: () => import("../components/RegisterForm"),
    meta: { guest: true },
  },

  {
    path: "/register-success",
    name: "Register Success",
    component: () => import("../components/RegisterSuccess"),
    meta: { guest: true }
  },

  {
    path: "/alerts",
    name: "Alerts",
    component: () => import("../components/Alerts"),
    meta: { requiresAuth: true },
  },
];

const router = new VueRouter({
  mode: "hash",
  base: process.env.BASE_URL,
  routes,
});

router.beforeEach((to, from, next) => {
  if (to.matched.some((record) => record.meta.requiresAuth)) {
    if (store.getters.isAuthenticated) {
      next();
      return;
    }
    next("/login");
  } else {
    next();
  }
});


export default router;