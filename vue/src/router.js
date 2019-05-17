import Vue from "vue";
import Router from "vue-router";
import Index from "./pages/Index.vue";
import Landing from "./pages/Landing.vue";
import Login from "./pages/Login.vue";
import Profile from "./pages/Profile.vue";
import MainNavbar from "./layout/MainNavbar.vue";
import MainFooter from "./layout/MainFooter.vue";
import Signup from "./pages/Signup.vue";
import Organization from "./pages/Organization.vue"
import About from "./pages/About.vue"
import Group from "./pages/Group.vue"

Vue.use(Router);

function guard(to, from, next) {
  if (localStorage.logged == "true") {
    next();
  } else {
    next("/login");
  }
}

export default new Router({
  linkExactActiveClass: "active",
  routes: [
    {
      path: "/",
      name: "index",
      components: { default: Index, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" }
      }
    },
    {
      path: "/landing",
      name: "landing",
      components: { default: Landing, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" }
      }
    },
    {
      path: "/login",
      name: "login",
      components: { default: Login, header: MainNavbar },
      props: {
        header: { colorOnScroll: 400 }
      },
      meta: {
        public: true,
        onlyWhenLoggedOut: true
      }
    },

    {
        path:"/organization",
        name:"organization",
        components: {default: Organization, header: MainNavbar, footer: MainFooter},
        props: {
          header: { colorOnScroll: 400 },
          footer: { backgroundColor: "black" }
        }
    },

    {
      path: "/profile",
      beforeEnter: guard,
      name: "profile",
      components: { default: Profile, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" }
      }
    },
    {
      path: "/group/:groupname",
      beforeEnter: guard,
      name: "groupParam",
      components: { default: Group, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" }
      }
    },
    {
      path: "/profile/:username",
      beforeEnter: guard,
      name: "profileParam",
      components: { default: Profile, header: MainNavbar, footer: MainFooter },
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" }
      }
    },
    {
      path: "/signup",
      name: "signup",
      components: { default: Signup, header: MainNavbar },
      props: {
        header: { colorOnScroll: 400 }
      }
    },
    {
      path:"/about",
      name:"about",
      components: {default: About, header: MainNavbar, footer: MainFooter},
      props: {
        header: { colorOnScroll: 400 },
        footer: { backgroundColor: "black" }
      }
    },
    {
      path: "*",
      redirect: "/" 
    }
  ],
  scrollBehavior: to => {
    if (to.hash) {
      return { selector: to.hash };
    } else {
      return { x: 0, y: 0 };
    }
  }
});
