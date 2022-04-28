<script>
import functions from "../utils/functions.vue";
import auth from "../utils/authorization-utils.vue";
import * as EmailValidator from "email-validator";
let toNormalize = ["email", "password", "username", "password-repeat"];
let ac = "auth-container-";

//Get session token of user
/*async function getToken() {
  var email = "admin@test.com";
  var password = "admin";
  var authorization = "Basic " + btoa(email + ":" + password);
  await $.ajax({
    type: "GET",
    url: "http://localhost:1048/login",
    dataType: "json",
    timeout: 0,
    headers: {
      Authorization: authorization,
    },
    success: function (data) {
      return data;
    },
    error: function (err) {
      return err;
    },
  });
}*/



function getData() {
var settings = {
  "url": "http://localhost:1048/login",
  "method": "GET",
  "timeout": 0,
  "headers": {
    "Authorization": "Basic YWRtaW5AdGVzdC5jb206YWRtaW4="
  },
};

$.ajax(settings).done(function (response) {
  console.log(response);
});
  var settings = {
    "url": "http://localhost:1048/login",
    "method": "GET",
    "timeout": 0,
    "headers": {
      "Authorization": "Basic ZGV2ZWxvcGVyQHRlc3QuY29tOjY1TFdLZmhVNGV1Q2czRjlReUJ5cWhobUpLNjR3Z2gyZGs1TnVhdndjU0s4OVJSVUVMVzg3d01ibVlFZE1LdDU="
    },
  };
  $.ajax(settings).done(function (response) {
    console.log(response);
  });
}


var data = (JSON.stringify(getData()));
console.log(data);

export default {
  name: "Authorization",
  methods: {
    validateLogin() {
      var email = document.getElementById(ac + "email").value;
      var password = document.getElementById(ac + "password").value;

      this.validEmail(email);
      this.validPassword(password);

      while (this.validEmail(email) && this.validPassword(password)) {
        functions.methods.show(["login-success"]);
        functions.methods.hide(["auth-window"]);
        /* getToken()
          .then(function (response) {
            console.log("Data loaded:", response);
          })
          .catch(function (err) {
            console.log(err);
          }); */

        auth.methods.setNormal(["email", "password"]);
        functions.methods.changeText("error-message", "");
        break;
      }
    },
    validateSignup() {
      var email = document.getElementById(ac + "email").value;
      var password = document.getElementById(ac + "password").value;
      var username = document.getElementById(ac + "username").value;
      var passwordRepeat = document.getElementById(
        ac + "password-repeat"
      ).value;

      this.validUsername(username);
      this.validEmail(email);
      this.validPassword(password);
      this.validPasswordRepeat(password, passwordRepeat);

      while (
        this.validUsername(username) &&
        this.validEmail(email) &&
        this.validPassword(password) &&
        this.validPasswordRepeat(password, passwordRepeat)
      ) {
        auth.methods.setNormal([
          "username",
          "email",
          "password",
          "password-repeat",
        ]);
        functions.methods.changeText("error-message", "");
        break;
      }
    },
    validate() {
      if (document.getElementById("auth-button").value === "Login") {
        functions.methods.resetAnimation("error-message");
        this.validateLogin();
      } else {
        this.validateSignup();
        functions.methods.resetAnimation("error-message");
      }
    },
    validUsername(username) {
      if (username.length === 0)
        return auth.methods.fieldIsNull(ac + "username");
      else {
        if (username.length < 4)
          return auth.methods.fieldIsUnderMinimum(ac + "username", 4);
        else if (username.length > 16)
          return auth.methods.fieldIsOverMaximum(ac + "username", 16);
        else auth.methods.setNormal(["username"]);
      }
      return true;
    },
    validEmail(email) {
      if (email.length === 0) return auth.methods.fieldIsNull(ac + "email");
      else {
        if (EmailValidator.validate(email) !== true)
          return auth.methods.wrongEmailSyntax(ac + "email");
        else auth.methods.setNormal(["email"]);
      }
      return true;
    },
    validPassword(password) {
      if (password.length === 0)
        return auth.methods.fieldIsNull(ac + "password");
      else {
        if (password.length < 8)
          return auth.methods.fieldIsUnderMinimum(ac + "password", 8);
        else auth.methods.setNormal(["password"]);
      }
      return true;
    },
    validPasswordRepeat(password, passwordRepeat) {
      if (passwordRepeat.length === 0)
        return auth.methods.fieldIsNull(ac + "password-repeat");
      else {
        if (passwordRepeat !== password)
          return auth.methods.passwordDoesntMatch(ac + "password-repeat");
        else if (password !== passwordRepeat)
          return auth.methods.passwordDoesntMatch(ac + "password-repeat");
        else auth.methods.setNormal(["password-repeat", "password"]);
      }
      return true;
    },
    showLogin() {
      let finalContainers = [];
      for (var i = 0; i < toNormalize.length; i++) {
        finalContainers.push(toNormalize[i]);
      }

      auth.methods.setActive("login-button", "signup-button");
      functions.methods.resetAnimation(ac + "inputs");
      functions.methods.changeTextAndValue("auth-button", "Login");

      functions.methods.hide([
        ac + "username",
        ac + "username-text",
        ac + "password-repeat",
        ac + "password-repeat-text",
      ]);

      functions.methods.addToClassList([ac.slice(0, -1), ac + "window"], "add");

      functions.methods.changeText("error-message", "");
      auth.methods.setActive("login-button", "signup-button");
      functions.methods.shrink([ac.slice(0, -1), ac + "window"], "add");
      functions.methods.extend([ac.slice(0, -1), ac + "window"], "remove");
      for (var i = 0; i < finalContainers.length; i++) {
        auth.methods.setNormal([finalContainers[i]]);
      }
    },
    showSignup() {
      let finalContainers = [];
      for (var i = 0; i < toNormalize.length; i++) {
        finalContainers.push(toNormalize[i]);
      }
      functions.methods.resetAnimation(ac + "inputs");
      functions.methods.changeTextAndValue("auth-button", "Sign-up");
      functions.methods.show([
        ac + "username",
        ac + "username-text",
        ac + "password-repeat",
        ac + "password-repeat-text",
      ]);

      functions.methods.changeText("error-message", "");
      auth.methods.setActive("signup-button", "login-button");
      functions.methods.shrink([ac.slice(0, -1), ac + "window"], "remove");
      functions.methods.extend([ac.slice(0, -1), ac + "window"], "add");
      auth.methods.setNormal(["password"]);

      for (var i = 0; i < finalContainers.length; i++) {
        auth.methods.setNormal([finalContainers[i]]);
      }
    },
  },
  created() {
    setTimeout(this.showLogin, 100);
  },
};
</script>

<template>
  <html lang="en">
    <body>
      <div>
        <h1
          id="title"
          class="font-bold text-8xl font-raleway top-8 relative select-none"
        >
          ZumHub
        </h1>
      </div>
      <div id="auth-container" class="container mx-auto relative top-32">
        <div
          id="auth-container-window"
          class="bg-white rounded-2xl container w-96 mx-auto shadow-2xl"
        >
          <div
            id="login-success"
            class="w-96 h-96 scale bg-white"
            style="display: none"
          >
            <div class="circle-wrap absolute left-[7rem] dropdown">
              <div class="circle">
                <div class="mask full">
                  <div class="fill"></div>
                </div>

                <div class="mask half">
                  <div class="fill"></div>
                </div>

                <div class="inside-circle">
                  <div
                    class="checkmark absolute w-12 h-12 left-10 top-10 scale"
                  >
                    <svg
                      class="fill-green-400"
                      xmlns="http://www.w3.org/2000/svg"
                      viewBox="0 0 448 512"
                    >
                      <path
                        d="M438.6 105.4C451.1 117.9 451.1 138.1 438.6 150.6L182.6 406.6C170.1 419.1 149.9 419.1 137.4 406.6L9.372 278.6C-3.124 266.1-3.124 245.9 9.372 233.4C21.87 220.9 42.13 220.9 54.63 233.4L159.1 338.7L393.4 105.4C405.9 92.88 426.1 92.88 438.6 105.4H438.6z"
                      />
                    </svg>
                  </div>
                </div>
              </div>
            </div>
            <div
              class="
                absolute
                container
                mx-auto
                bottom-8
                font-raleway
                text-xl
                font-bold
                select-none
              "
            >
              Redirecting...
            </div>
          </div>
          <div id="auth-window">
            <div id="auth-container-buttons" class="grid gap-0.5 grid-cols-2">
              <div>
                <button
                  type="button"
                  id="login-button"
                  @click="showLogin()"
                  class="
                    rounded-tl-2xl
                    bg-slate-200
                    h-12
                    w-48
                    hover:bg-amber-400
                    font-bold
                    text-xl
                    transition
                    duration-500
                    ease-out
                  "
                >
                  Login
                </button>
              </div>
              <div>
                <button
                  type="button"
                  id="signup-button"
                  @click="showSignup()"
                  class="
                    rounded-tr-2xl
                    bg-slate-200
                    h-12
                    w-48
                    hover:bg-amber-400
                    font-bold
                    text-xl
                    transition
                    duration-500
                    ease-out
                  "
                >
                  Sign-up
                </button>
              </div>
              <div
                id="error-message"
                style="color: red"
                class="
                  absolute
                  text-sm
                  container
                  mx-auto
                  w-96
                  top-12
                  bg-red-200
                  scale
                "
                value=""
              ></div>
            </div>
            <form
              id="auth-container-inputs"
              class="
                grid
                gap-4
                h-32
                auto-cols-auto
                mx-auto
                relative
                left-12
                top-8
                fadeInclass
              "
            >
              <label
                id="auth-container-username-text"
                for="auth-container-username"
                class="relative right-36 text-lg font-bold"
                >Username<span style="color: red"> *</span></label
              >
              <input
                id="auth-container-username"
                type="text"
                class="
                  bg-slate-100
                  w-72
                  h-12
                  rounded-3xl
                  border-2
                  text-xl
                  indent-2.5
                "
              />
              <label
                id="auth-container-email-text"
                for="auth-container-email"
                class="relative right-40 text-lg font-bold"
                >Email<span style="color: red"> *</span></label
              >
              <input
                id="auth-container-email"
                type="text"
                class="
                  bg-slate-100
                  w-72
                  h-12
                  rounded-3xl
                  border-2
                  text-xl
                  indent-2.5
                "
              />
              <label
                for="auth-container-password"
                class="relative right-36 text-lg font-bold"
                >Password<span style="color: red"> *</span></label
              >
              <input
                id="auth-container-password"
                type="password"
                class="
                  bg-slate-100
                  w-72
                  h-12
                  rounded-3xl
                  border-2
                  text-xl
                  indent-2.5
                "
              />
              <label
                id="auth-container-password-repeat-text"
                for="auth-container-password-repeat"
                class="relative right-28 text-lg font-bold"
                >Repeat Password<span style="color: red"> *</span></label
              >
              <input
                id="auth-container-password-repeat"
                type="password"
                class="
                  bg-slate-100
                  w-72
                  h-12
                  rounded-3xl
                  border-2
                  text-xl
                  indent-2.5
                "
              />
            </form>
            <div
              id="confirm-button"
              class="bottom-6 absolute right-0 container mx-auto"
            >
              <button
                type="button"
                @click="validate()"
                value=""
                id="auth-button"
                class="
                  rounded-3xl
                  w-72
                  h-12
                  bg-amber-300
                  text-xl
                  font-bold
                  hover:bg-amber-400
                "
              ></button>
            </div>
          </div>
        </div>
      </div>
    </body>
  </html>
</template>

<style>
.extend {
  height: 600px;
  transition: height 0.5s ease-out;
}
.shrink {
  height: 384px;
  transition: height 0.5s ease-out;
}
.fadeInclass {
  animation: fadeIn forwards ease-in 1s;
  opacity: 0;
}
.warning {
  border: 1px solid red !important;
}

.checkmark {
  transform: scale(0);
}

.circle-wrap {
  width: 150px;
  height: 150px;
  background: #e6e2e7;
  border-radius: 50%;
  animation: dropdown forwards ease-in-out 1s;
  top: 0px;
}

.circle-wrap .circle .mask,
.circle-wrap .circle .fill {
  width: 150px;
  height: 150px;
  position: absolute;
  border-radius: 50%;
}

.circle-wrap .circle .mask {
  clip: rect(0px, 150px, 150px, 75px);
}

.circle-wrap .circle .mask .fill {
  clip: rect(0px, 75px, 150px, 0px);
  background-color: #fcd34d;
}
.circle-wrap .circle .mask.full,
.circle-wrap .circle .fill {
  animation: fill ease-in-out 1s;
  transform: rotate(180deg);
}
@keyframes fill {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(180deg);
  }
}

@keyframes dropdown {
  0% {
    top: 0px;
  }
  100% {
    top: 120px;
  }
}

.circle-wrap .inside-circle {
  width: 130px;
  height: 130px;
  border-radius: 50%;
  line-height: 130px;
  background-color: white;
  text-align: center;
  margin-top: 10px;
  margin-left: 10px;
  position: absolute;
  z-index: 100;
  font-weight: 700;
  font-size: 2em;
}

.scale {
  animation: scale forwards ease-out 1s;
  transform: scale(0);
}

@keyframes fadeIn {
  100% {
    opacity: 1;
  }
}

@keyframes scale {
  100% {
    transform: scale(1);
  }
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
