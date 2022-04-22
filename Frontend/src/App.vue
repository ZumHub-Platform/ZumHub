<script>
//import SHA1 from "crypto-js/sha1";
import functions from "../../Frontend/src/components/functions.vue";
import * as EmailValidator from 'email-validator';

//Set the input field to normal form, when are conditions done
function setNormal(container) {
  $("#" + container).removeClass("bg-red-100");
  $("#" + container).removeClass("border-red-500");
  $("#" + container).addClass("bg-slate-100");
}
//Show which input field is empty, and needs to be filled
function fieldIsNull(container) {
  functions.methods.changeText(
    "error-message",
    "You must fill all required fields."
  );
  $("#" + container).removeClass("bg-slate-100");
  $("#" + container).addClass("bg-red-100");
  $("#" + container).addClass("border-red-500");
}
//Check if the input field isn't below the conditions
function fieldIsUnderMinimum(container, minimum) {
  functions.methods.changeText(
    "error-message",
    `This field has less than ${minimum} characters`
  );
  $("#" + container).removeClass("bg-slate-100");
  $("#" + container).addClass("bg-red-100");
  $("#" + container).addClass("border-red-500");
}
//Check if the input field isn't over the conditions
function fieldIsOverMaximum(container, maximum) {
  functions.methods.changeText(
    "error-message",
    `This field has more than ${maximum} characters`
  );
  $("#" + container).removeClass("bg-slate-100");
  $("#" + container).addClass("bg-red-100");
  $("#" + container).addClass("border-red-500");
}
//Check if password in password repeat field doesn't match
function passwordDoesntMatch(container) {
  functions.methods.changeText("error-message", "Password doesn't match.");
  $("#" + container).removeClass("bg-slate-100");
  $("#" + container).addClass("bg-red-100");
  $("#" + container).addClass("border-red-500");
}
//Check the email syntax, and mark if it's invalid
function wrongEmailSyntax(container) {
  functions.methods.changeText("error-message", "Invalid email.");
  $("#" + container).removeClass("bg-slate-100");
  $("#" + container).addClass("bg-red-100");
  $("#" + container).addClass("border-red-500");
}
//Get session token of user
function getToken() {
  var username = "admin@test.com";
  var password = "admin";
  var result = "";
  $.ajax({
    type: "GET",
    url: "http://localhost:1048/login",
    dataType: "json",
    async: false,
    headers: {
      Authorization: "Basic " + btoa(username + ":" + password),
    },
    success: function (data) {
      result = data;
    },
    error: function (data) {
      result = data;
    },
  });
  return result;
}
//Reset all input fields to normal look, when all conditions are met
function resetInputEffects(container) {
  for (var i = 0; i < container.length; i++) {
    document.getElementById("error-message").innerText = ``;
    $("#" + container[i]).removeClass("outline-red-500");
    $("#" + container[i]).removeClass("bg-red-100");
    $("#" + container[i]).removeClass("border-red-500");
    $("#" + container[i]).addClass("bg-slate-100");
  }
}
//Show login box and hide signup box
function showLogin() {
  functions.methods.resetAnimation("auth-container-inputs");

  functions.methods.changeValue("auth-button", "Login");
  functions.methods.changeText("auth-button", "Login");

  functions.methods.hide([
    "auth-container-username",
    "auth-container-username-text",
    "auth-container-password-repeat",
    "auth-container-password-repeat-text",
  ]);
  functions.methods.show(["auth-container-email", "auth-container-email-text"]);

  functions.methods.addToClassList("auth-container-window", "shrink");
  functions.methods.addToClassList("auth-container", "shrink");
}
//Show signup box and hide login
function showSignup() {
  functions.methods.resetAnimation("auth-container-inputs");

  functions.methods.changeValue("auth-button", "Sign-up");
  functions.methods.changeText("auth-button", "Sign-up");

  functions.methods.show([
    "auth-container-username",
    "auth-container-username-text",
    "auth-container-email",
    "auth-container-email-text",
    "auth-container-password-repeat",
    "auth-container-password-repeat-text",
  ]);
}
async function isEmailValid(email) {
  return EmailValidator.validate(email);
}
//Check if all conditions are met for signup form
async function fieldInSignUpIsFilled() {
  var username = document.getElementById("auth-container-username").value;
  var email = document.getElementById("auth-container-email").value;
  var password = document.getElementById("auth-container-password").value;
  var passwordrepeat = document.getElementById(
    "auth-container-password-repeat"
  ).value;

  if (password.length === 0) fieldIsNull("auth-container-password");
  else {
    if (password.length <= 7) fieldIsUnderMinimum("auth-container-password", 8);
    else setNormal("auth-container-password");
  }
  if (username.length === 0) fieldIsNull("auth-container-username");
  else {
    if (username.length <= 3) fieldIsUnderMinimum("auth-container-username", 4);
    else if (username.length > 16)
      fieldIsOverMaximum("auth-container-container", 16);
    else setNormal("auth-container-username");
  }
  if (email.length === 0) fieldIsNull("auth-container-email");
  else {
    if (await isEmailValid(email) !== true) wrongEmailSyntax("auth-container-email");
    else setNormal("auth-container-email");
  }
  if (passwordrepeat.length === 0)
    fieldIsNull("auth-container-password-repeat");
  else {
    if (passwordrepeat !== password)
      passwordDoesntMatch("auth-container-password-repeat");
    else setNormal("auth-container-password-repeat");
    setNormal("auth-container-password");
  }

  while (
    username.length !== 0 &&
    email.length !== 0 &&
    password.length !== 0 &&
    passwordrepeat.length !== 0 &&
    username.length > 4 &&
    username.length < 16 &&
    password.length > 7 &&
    await isEmailValid(email) === true &&
    passwordrepeat === password
  ) {
    resetInputEffects([
      "auth-container-username",
      "auth-container-email",
      "auth-container-password",
      "auth-container-password-repeat",
    ]);
    break;
  }
}
//Check if all conditions are met for login form
async function fieldInLoginIsFilled() {
  var email = document.getElementById("auth-container-email").value;
  var password = document.getElementById("auth-container-password").value;

  if (password.length === 0) fieldIsNull("auth-container-password");
  if (email.length === 0) fieldIsNull("auth-container-email");
  else {
    if (await isEmailValid(email) !== true) wrongEmailSyntax("auth-container-email");
    else setNormal("auth-container-email");
  }

  while (
    email.length !== 0 &&
    password.length !== 0 &&
    await isEmailValid(email) === true
  ) {
    sessionStorage.setItem("token", JSON.stringify(getToken()));
    console.log("token:", JSON.parse(sessionStorage.getItem("token")));

    resetInputEffects(["auth-container-email", "auth-container-password"]);
    break;
  }
}

$(document).ready(function () {
  $("#login-button").removeClass("bg-slate-200");
  $("#login-button").addClass("bg-amber-300");

  $("#login-button").click(function () {
    let containers = [
      "auth-container-email",
      "auth-container-password",
      "auth-container-username",
      "auth-container-password-repeat",
    ];
    functions.methods.changeText("error-message", "");
    $(this).removeClass("bg-slate-200");
    $(this).addClass("bg-amber-300");
    $("#auth-container").addClass("shrink");
    $("#auth-container-window").addClass("shrink");
    $("#auth-container").removeClass("extend");
    $("#auth-container-window").removeClass("extend");
    $("#signup-button").removeClass("bg-amber-300");
    $("#signup-button").addClass("bg-slate-200");
    for (var i = 0; i < containers.length; i++) {
      setNormal(containers[i]);
    }
  });
  $("#signup-button").click(function () {
    let containers = [
      "auth-container-email",
      "auth-container-password",
      "auth-container-username",
      "auth-container-password-repeat",
    ];
    functions.methods.changeText("error-message", "");
    $(this).removeClass("bg-slate-200");
    $(this).addClass("bg-amber-300");
    $("#auth-container").addClass("extend");
    $("#auth-container-window").addClass("extend");
    $("#auth-container").removeClass("shrink");
    $("#auth-container-window").removeClass("shrink");
    $("#login-button").removeClass("bg-amber-300");
    $("#login-button").addClass("bg-slate-200");
    $("#auth-container-password").removeClass("outline-red-500");
    for (var i = 0; i < containers.length; i++) {
      setNormal(containers[i]);
    }
  });

  $("#auth-button").click(function () {
    if (document.getElementById("auth-button").value === "Sign-up") {
      fieldInSignUpIsFilled();
    } else {
      fieldInLoginIsFilled();
    }
  });
});

window.onload = showLogin;

export default {
  setup() {
    return {
      showLogin,
      showSignup,
    };
  },
};
</script>

<template>
  <html lang="en">
    <body>
      <div>
        <h1 id="title" class="font-bold text-8xl font-raleway top-8 relative">
          ZumHub
        </h1>
      </div>
      <div id="auth-container" class="container mx-auto relative top-32">
        <div
          id="auth-container-window"
          class="bg-white rounded-2xl container w-96 mx-auto shadow-2xl"
        >
          <div id="auth-container-buttons" class="grid gap-0.5 grid-cols-2">
            <div>
              <button
                type="button"
                id="login-button"
                @click="showLogin"
                class="
                  rounded-tl-2xl
                  bg-slate-200
                  h-12
                  w-48
                  hover:bg-amber-400
                  font-bold
                  text-xl
                "
              >
                Login
              </button>
            </div>
            <div>
              <button
                type="button"
                id="signup-button"
                @click="showSignup"
                class="
                  rounded-tr-2xl
                  bg-slate-200
                  h-12
                  w-48
                  hover:bg-amber-400
                  font-bold
                  text-xl
                "
              >
                Sign-up
              </button>
            </div>
          </div>
          <p
            id="error-message"
            style="color: red"
            class="absolute text-sm container mx-auto w-96 top-14"
            value=""
          ></p>
          <div
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
              >Username</label
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
              >Email</label
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
              >Password</label
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
              >Repeat Password</label
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
          </div>
          <div
            id="confirm-button"
            class="bottom-6 absolute right-0 container mx-auto"
          >
            <button
              type="button"
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

@keyframes fadeIn {
  100% {
    opacity: 1;
  }
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
