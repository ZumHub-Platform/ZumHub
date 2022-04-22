<script>
import functions from "../../Frontend/src/components/functions.vue";
function hideOnLoad() {
  functions.methods.hide(["totalwords"]);
}

$(document).ready(function () {
  $("#chat-bar").on("keyup", function () {
    let chatbar = document.getElementById("chat-bar").value;
    let lengthOfChatBar = chatbar.length;
    if (lengthOfChatBar > 0) {
      functions.methods.show(["totalwords"]);
      functions.methods.changeText("totalwords", `${lengthOfChatBar}/1000`);
      if (lengthOfChatBar > 999) {
        $("#totalwords").removeClass("text-slate-500");
        $("#totalwords").addClass("text-red-500");
        $("#totalwords").addClass("animate-bounce");
      } else {
        $("#totalwords").addClass("text-slate-500");
        $("#totalwords").removeClass("text-red-500");
        $("#totalwords").removeClass("animate-bounce");
      }
    } else if (chatbar.length === 0) {
      functions.methods.hide(["totalwords"]);
      functions.methods.changeText("totalwords", ``);
      $("#totalwords").addClass("text-slate-500");
      $("#totalwords").removeClass("text-red-500");
      $("#totalwords").removeClass("animate-bounce");
    }
  });
});

window.onload = hideOnLoad;
</script>

<template>
  <html lang="en">
    <body>
      <div id="main">
        <div id="left-sidebar" class="h-full">
          <div id="chatbox-container">
            <div
              id="chat-action-bar-container"
              class="w-full bg-slate-200 top-0 h-16 left-0 absolute"
            >
              <h1
                id="hashtag"
                class="
                  left-96
                  top-3
                  text-4xl text-black
                  absolute
                  font-mono
                  cursor-pointer
                  select-none
                "
              >
                # channel-name
              </h1>
            </div>
            <div
              id="chat-container"
              class="w-full bg-slate-600 top-16 bottom-0 left-0 absolute"
            >
              <div
                id="chat-bar-container"
                class="
                  rounded-3xl
                  pr-96
                  left-96
                  h-24
                  bottom-16
                  right-16
                  absolute
                  bg-slate-800
                "
              >
                <textarea
                  maxlength="1000"
                  placeholder="Start typing..."
                  type="text"
                  id="chat-bar"
                  class="
                    bg-slate-800
                    h-12
                    bottom-10
                    text-lg
                    left-6
                    right-24
                    absolute
                    resize-none
                    overflow-hidden
                    break-words
                    outline-none
                    text-white
                    align-middle
                    font-mono
                  "
                ></textarea>
                <hr
                  class="
                    w-full
                    bg-slate-500
                    bottom-10
                    h-0.5
                    left-0
                    absolute
                    border-none
                  "
                />
                <h3
                  id="totalwords"
                  class="right-2 top-5 absolute text-xl text-slate-500"
                ></h3>
                <div
                  id="chat-effects-bar"
                  class="bottom-1.5 left-4 absolute flex justify-start space-x-4"
                >
                  <div class="w-7 h-7 rounded-full cursor-pointer">
                    <svg
                      class="w-5 h-5 fill-slate-400 hover:fill-slate-300 translate-x-1 translate-y-1"
                      xmlns="http://www.w3.org/2000/svg"
                      viewBox="0 0 320 512"
                    >
                      <path
                        d="M257.1 242.4C276.1 220.1 288 191.6 288 160c0-70.58-57.42-128-128-128H32c-17.67 0-32 14.33-32 32v384c0 17.67 14.33 32 32 32l160-.0049c70.58 0 128-57.42 128-128C320 305.3 294.6 264.8 257.1 242.4zM64 96.01h96c35.3 0 64 28.7 64 64s-28.7 64-64 64H64V96.01zM192 416H64v-128h128c35.3 0 64 28.7 64 64S227.3 416 192 416z"
                      />
                    </svg>
                  </div>
                  <div class="w-7 h-7 rounded-full cursor-pointer">
                    <svg
                      class="w-5 h-5 fill-slate-400 hover:fill-slate-300 translate-x-1 translate-y-1"
                      xmlns="http://www.w3.org/2000/svg"
                      viewBox="0 0 384 512"
                    >
                      <path
                        d="M384 64.01c0 17.69-14.31 32-32 32h-58.67l-133.3 320H224c17.69 0 32 14.31 32 32s-14.31 32-32 32H32c-17.69 0-32-14.31-32-32s14.31-32 32-32h58.67l133.3-320H160c-17.69 0-32-14.31-32-32s14.31-32 32-32h192C369.7 32.01 384 46.33 384 64.01z"
                      />
                    </svg>
                  </div>
                  <div class="w-7 h-7 rounded-full cursor-pointer">
                    <svg
                      class="w-5 h-5 fill-slate-400 hover:fill-slate-300 translate-x-1 translate-y-1"
                      xmlns="http://www.w3.org/2000/svg"
                      viewBox="0 0 448 512"
                    >
                      <path
                        d="M416 448H32c-17.69 0-32 14.31-32 32s14.31 32 32 32h384c17.69 0 32-14.31 32-32S433.7 448 416 448zM48 64.01H64v160c0 88.22 71.78 159.1 160 159.1s160-71.78 160-159.1v-160h16c17.69 0 32-14.32 32-32s-14.31-31.1-32-31.1l-96-.0049c-17.69 0-32 14.32-32 32s14.31 32 32 32H320v160c0 52.94-43.06 95.1-96 95.1S128 276.1 128 224v-160h16c17.69 0 32-14.31 32-32s-14.31-32-32-32l-96 .0049c-17.69 0-32 14.31-32 31.1S30.31 64.01 48 64.01z"
                      />
                    </svg>
                  </div>
                  <div class="w-7 h-7 rounded-full cursor-pointer">
                    <svg
                      class="w-5 h-5 fill-slate-400 hover:fill-slate-300 translate-x-1 translate-y-1"
                      xmlns="http://www.w3.org/2000/svg"
                      viewBox="0 0 512 512"
                    >
                      <path
                        d="M332.2 319.9c17.22 12.17 22.33 26.51 18.61 48.21c-3.031 17.59-10.88 29.34-24.72 36.99c-35.44 19.75-108.5 11.96-186-19.68c-16.34-6.686-35.03 1.156-41.72 17.53s1.188 35.05 17.53 41.71c31.75 12.93 95.69 35.37 157.6 35.37c29.62 0 58.81-5.156 83.72-18.96c30.81-17.09 50.44-45.46 56.72-82.11c3.998-23.27 2.168-42.58-3.488-59.05H332.2zM488 239.9l-176.5-.0309c-15.85-5.613-31.83-10.34-46.7-14.62c-85.47-24.62-110.9-39.05-103.7-81.33c2.5-14.53 10.16-25.96 22.72-34.03c20.47-13.15 64.06-23.84 155.4 .3438c17.09 4.531 34.59-5.654 39.13-22.74c4.531-17.09-5.656-34.59-22.75-39.12c-91.31-24.18-160.7-21.62-206.3 7.654C121.8 73.72 103.6 101.1 98.09 133.1C89.26 184.5 107.9 217.3 137.2 239.9L24 239.9c-13.25 0-24 10.75-24 23.1c0 13.25 10.75 23.1 24 23.1h464c13.25 0 24-10.75 24-23.1C512 250.7 501.3 239.9 488 239.9z"
                      />
                    </svg>
                  </div>
                  <div class="w-7 h-7 rounded-full cursor-pointer">
                    <svg
                      class="w-5 h-5 fill-slate-400 hover:fill-slate-300 translate-x-1 translate-y-1"
                      xmlns="http://www.w3.org/2000/svg"
                      viewBox="0 0 640 512"
                    >
                      <path
                        d="M414.8 40.79L286.8 488.8C281.9 505.8 264.2 515.6 247.2 510.8C230.2 505.9 220.4 488.2 225.2 471.2L353.2 23.21C358.1 6.216 375.8-3.624 392.8 1.232C409.8 6.087 419.6 23.8 414.8 40.79H414.8zM518.6 121.4L630.6 233.4C643.1 245.9 643.1 266.1 630.6 278.6L518.6 390.6C506.1 403.1 485.9 403.1 473.4 390.6C460.9 378.1 460.9 357.9 473.4 345.4L562.7 256L473.4 166.6C460.9 154.1 460.9 133.9 473.4 121.4C485.9 108.9 506.1 108.9 518.6 121.4V121.4zM166.6 166.6L77.25 256L166.6 345.4C179.1 357.9 179.1 378.1 166.6 390.6C154.1 403.1 133.9 403.1 121.4 390.6L9.372 278.6C-3.124 266.1-3.124 245.9 9.372 233.4L121.4 121.4C133.9 108.9 154.1 108.9 166.6 121.4C179.1 133.9 179.1 154.1 166.6 166.6V166.6z"
                      />
                    </svg>
                  </div>
                  <div class="w-7 h-7 rounded-full cursor-pointer">
                    <svg
                      class="w-5 h-5 fill-slate-400 hover:fill-slate-300 translate-x-1 translate-y-1"
                      xmlns="http://www.w3.org/2000/svg"
                      viewBox="0 0 640 512"
                    >
                      <path
                        d="M172.5 131.1C228.1 75.51 320.5 75.51 376.1 131.1C426.1 181.1 433.5 260.8 392.4 318.3L391.3 319.9C381 334.2 361 337.6 346.7 327.3C332.3 317 328.9 297 339.2 282.7L340.3 281.1C363.2 249 359.6 205.1 331.7 177.2C300.3 145.8 249.2 145.8 217.7 177.2L105.5 289.5C73.99 320.1 73.99 372 105.5 403.5C133.3 431.4 177.3 435 209.3 412.1L210.9 410.1C225.3 400.7 245.3 404 255.5 418.4C265.8 432.8 262.5 452.8 248.1 463.1L246.5 464.2C188.1 505.3 110.2 498.7 60.21 448.8C3.741 392.3 3.741 300.7 60.21 244.3L172.5 131.1zM467.5 380C411 436.5 319.5 436.5 263 380C213 330 206.5 251.2 247.6 193.7L248.7 192.1C258.1 177.8 278.1 174.4 293.3 184.7C307.7 194.1 311.1 214.1 300.8 229.3L299.7 230.9C276.8 262.1 280.4 306.9 308.3 334.8C339.7 366.2 390.8 366.2 422.3 334.8L534.5 222.5C566 191 566 139.1 534.5 108.5C506.7 80.63 462.7 76.99 430.7 99.9L429.1 101C414.7 111.3 394.7 107.1 384.5 93.58C374.2 79.2 377.5 59.21 391.9 48.94L393.5 47.82C451 6.731 529.8 13.25 579.8 63.24C636.3 119.7 636.3 211.3 579.8 267.7L467.5 380z"
                      />
                    </svg>
                  </div>
                  <div class="w-7 h-7 rounded-full cursor-pointer">
                    <svg
                      class="w-5 h-5 fill-slate-400 hover:fill-slate-300 translate-x-1 translate-y-1"
                      xmlns="http://www.w3.org/2000/svg"
                      viewBox="0 0 576 512"
                    >
                      <path
                        d="M55.1 56.04C55.1 42.78 66.74 32.04 79.1 32.04H111.1C125.3 32.04 135.1 42.78 135.1 56.04V176H151.1C165.3 176 175.1 186.8 175.1 200C175.1 213.3 165.3 224 151.1 224H71.1C58.74 224 47.1 213.3 47.1 200C47.1 186.8 58.74 176 71.1 176H87.1V80.04H79.1C66.74 80.04 55.1 69.29 55.1 56.04V56.04zM118.7 341.2C112.1 333.8 100.4 334.3 94.65 342.4L83.53 357.9C75.83 368.7 60.84 371.2 50.05 363.5C39.26 355.8 36.77 340.8 44.47 330.1L55.59 314.5C79.33 281.2 127.9 278.8 154.8 309.6C176.1 333.1 175.6 370.5 153.7 394.3L118.8 432H152C165.3 432 176 442.7 176 456C176 469.3 165.3 480 152 480H64C54.47 480 45.84 474.4 42.02 465.6C38.19 456.9 39.9 446.7 46.36 439.7L118.4 361.7C123.7 355.9 123.8 347.1 118.7 341.2L118.7 341.2zM512 64C529.7 64 544 78.33 544 96C544 113.7 529.7 128 512 128H256C238.3 128 224 113.7 224 96C224 78.33 238.3 64 256 64H512zM512 224C529.7 224 544 238.3 544 256C544 273.7 529.7 288 512 288H256C238.3 288 224 273.7 224 256C224 238.3 238.3 224 256 224H512zM512 384C529.7 384 544 398.3 544 416C544 433.7 529.7 448 512 448H256C238.3 448 224 433.7 224 416C224 398.3 238.3 384 256 384H512z"
                      />
                    </svg>
                  </div>
                </div>
                <div
                  id="chat-right-icon-bar"
                  class="bottom-1.5 right-4 absolute flex justify-end space-x-4"
                >
                  <div class="w-7 h-7 rounded-full cursor-pointer">
                    <svg
                      class="w-5 h-5 fill-slate-400 hover:fill-slate-300 translate-x-1 translate-y-1"
                      xmlns="http://www.w3.org/2000/svg"
                      viewBox="0 0 512 512"
                    >
                      <path
                        d="M0 256C0 114.6 114.6 0 256 0C397.4 0 512 114.6 512 256C512 397.4 397.4 512 256 512C114.6 512 0 397.4 0 256zM256.3 331.8C208.9 331.8 164.1 324.9 124.5 312.8C112.2 309 100.2 319.7 105.2 331.5C130.1 390.6 188.4 432 256.3 432C324.2 432 382.4 390.6 407.4 331.5C412.4 319.7 400.4 309 388.1 312.8C348.4 324.9 303.7 331.8 256.3 331.8H256.3zM176.4 176C158.7 176 144.4 190.3 144.4 208C144.4 225.7 158.7 240 176.4 240C194 240 208.4 225.7 208.4 208C208.4 190.3 194 176 176.4 176zM336.4 240C354 240 368.4 225.7 368.4 208C368.4 190.3 354 176 336.4 176C318.7 176 304.4 190.3 304.4 208C304.4 225.7 318.7 240 336.4 240z"
                      />
                    </svg>
                  </div>
                  <div class="w-7 h-7 rounded-full cursor-pointer">
                    <svg
                      class="w-5 h-5 fill-slate-400 hover:fill-slate-300 translate-x-1 translate-y-1"
                      xmlns="http://www.w3.org/2000/svg"
                      viewBox="0 0 512 512"
                    >
                      <path
                        d="M0 256C0 114.6 114.6 0 256 0C397.4 0 512 114.6 512 256C512 397.4 397.4 512 256 512C114.6 512 0 397.4 0 256zM256 368C269.3 368 280 357.3 280 344V280H344C357.3 280 368 269.3 368 256C368 242.7 357.3 232 344 232H280V168C280 154.7 269.3 144 256 144C242.7 144 232 154.7 232 168V232H168C154.7 232 144 242.7 144 256C144 269.3 154.7 280 168 280H232V344C232 357.3 242.7 368 256 368z"
                      />
                    </svg>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div
            id="server-list-container"
            class="w-16 bg-slate-400 container mx-auto top-0 bottom-0 absolute"
          >
            <div id="sidebar-top-container">
              <div
                id="platform-icon-container"
                class="
                  w-12
                  h-12
                  rounded-full
                  top-2
                  left-2
                  absolute
                  bg-slate-300
                  cursor-pointer
                "
              ></div>
              <div
                id="create-new-server-container"
                class="
                  w-12
                  h-12
                  rounded-full
                  top-20
                  left-2
                  absolute
                  bg-slate-300
                  cursor-pointer
                  hover:bg-blue-500
                  hover:text-white
                  hover:rounded-2xl
                  easy-out
                  transition
                  duration-500
                "
              >
                <h2
                  id="plus-icon"
                  class="
                    w-full
                    font-bold font-raleway
                    top-1
                    text-4xl
                    left-0
                    absolute
                    text-blue-500
                    cursor-pointer
                    hover:text-white
                  "
                >
                  +
                </h2>
              </div>
            </div>
            <div id="sidebar-management-container" class="cursor-pointer">
              <hr
                class="
                  w-full
                  bg-slate-500
                  bottom-16
                  h-0.5
                  left-0
                  absolute
                  border-none
                  cursor-default
                "
              />
              <div
                id="user-profile-icon-container"
                class="
                  w-12
                  bg-slate-600
                  h-12
                  left-2
                  bottom-2
                  rounded-full
                  absolute
                "
              ></div>
              <div
                id="user-settings-container"
                class="
                  w-12
                  h-12
                  left-2
                  bottom-20
                  rounded-full
                  bg-slate-300
                  absolute
                "
              >
                <svg
                  class="
                    w-8
                    h-8
                    absolute
                    left-2
                    top-2
                    fill-slate-600
                    container
                    mx-auto
                  "
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 640 512"
                >
                  <path
                    d="M286.3 155.1C287.4 161.9 288 168.9 288 175.1C288 183.1 287.4 190.1 286.3 196.9L308.5 216.7C315.5 223 318.4 232.1 314.7 241.7C312.4 246.1 309.9 252.2 307.1 257.2L304 262.6C300.1 267.6 297.7 272.4 294.2 277.1C288.5 284.7 278.5 287.2 269.5 284.2L241.2 274.9C230.5 283.8 218.3 290.9 205 295.9L198.1 324.9C197 334.2 189.8 341.6 180.4 342.8C173.7 343.6 166.9 344 160 344C153.1 344 146.3 343.6 139.6 342.8C130.2 341.6 122.1 334.2 121 324.9L114.1 295.9C101.7 290.9 89.5 283.8 78.75 274.9L50.53 284.2C41.54 287.2 31.52 284.7 25.82 277.1C22.28 272.4 18.98 267.5 15.94 262.5L12.92 257.2C10.13 252.2 7.592 247 5.324 241.7C1.62 232.1 4.458 223 11.52 216.7L33.7 196.9C32.58 190.1 31.1 183.1 31.1 175.1C31.1 168.9 32.58 161.9 33.7 155.1L11.52 135.3C4.458 128.1 1.62 119 5.324 110.3C7.592 104.1 10.13 99.79 12.91 94.76L15.95 89.51C18.98 84.46 22.28 79.58 25.82 74.89C31.52 67.34 41.54 64.83 50.53 67.79L78.75 77.09C89.5 68.25 101.7 61.13 114.1 56.15L121 27.08C122.1 17.8 130.2 10.37 139.6 9.231C146.3 8.418 153.1 8 160 8C166.9 8 173.7 8.418 180.4 9.23C189.8 10.37 197 17.8 198.1 27.08L205 56.15C218.3 61.13 230.5 68.25 241.2 77.09L269.5 67.79C278.5 64.83 288.5 67.34 294.2 74.89C297.7 79.56 300.1 84.42 304 89.44L307.1 94.83C309.9 99.84 312.4 105 314.7 110.3C318.4 119 315.5 128.1 308.5 135.3L286.3 155.1zM160 127.1C133.5 127.1 112 149.5 112 175.1C112 202.5 133.5 223.1 160 223.1C186.5 223.1 208 202.5 208 175.1C208 149.5 186.5 127.1 160 127.1zM484.9 478.3C478.1 479.4 471.1 480 464 480C456.9 480 449.9 479.4 443.1 478.3L423.3 500.5C416.1 507.5 407 510.4 398.3 506.7C393 504.4 387.8 501.9 382.8 499.1L377.4 496C372.4 492.1 367.6 489.7 362.9 486.2C355.3 480.5 352.8 470.5 355.8 461.5L365.1 433.2C356.2 422.5 349.1 410.3 344.1 397L315.1 390.1C305.8 389 298.4 381.8 297.2 372.4C296.4 365.7 296 358.9 296 352C296 345.1 296.4 338.3 297.2 331.6C298.4 322.2 305.8 314.1 315.1 313L344.1 306.1C349.1 293.7 356.2 281.5 365.1 270.8L355.8 242.5C352.8 233.5 355.3 223.5 362.9 217.8C367.6 214.3 372.5 210.1 377.5 207.9L382.8 204.9C387.8 202.1 392.1 199.6 398.3 197.3C407 193.6 416.1 196.5 423.3 203.5L443.1 225.7C449.9 224.6 456.9 224 464 224C471.1 224 478.1 224.6 484.9 225.7L504.7 203.5C511 196.5 520.1 193.6 529.7 197.3C535 199.6 540.2 202.1 545.2 204.9L550.5 207.9C555.5 210.1 560.4 214.3 565.1 217.8C572.7 223.5 575.2 233.5 572.2 242.5L562.9 270.8C571.8 281.5 578.9 293.7 583.9 306.1L612.9 313C622.2 314.1 629.6 322.2 630.8 331.6C631.6 338.3 632 345.1 632 352C632 358.9 631.6 365.7 630.8 372.4C629.6 381.8 622.2 389 612.9 390.1L583.9 397C578.9 410.3 571.8 422.5 562.9 433.2L572.2 461.5C575.2 470.5 572.7 480.5 565.1 486.2C560.4 489.7 555.6 492.1 550.6 496L545.2 499.1C540.2 501.9 534.1 504.4 529.7 506.7C520.1 510.4 511 507.5 504.7 500.5L484.9 478.3zM512 352C512 325.5 490.5 304 464 304C437.5 304 416 325.5 416 352C416 378.5 437.5 400 464 400C490.5 400 512 378.5 512 352z"
                  />
                </svg>
              </div>
              <div
                id="user-info-container"
                class="
                  w-12
                  h-12
                  left-2
                  bottom-36
                  rounded-full
                  bg-slate-300
                  absolute
                "
              >
                <svg
                  class="
                    w-8
                    h-8
                    absolute
                    left-2
                    top-2
                    fill-slate-600
                    container
                    mx-auto
                  "
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 512 512"
                >
                  <path
                    d="M256 0C114.6 0 0 114.6 0 256s114.6 256 256 256s256-114.6 256-256S397.4 0 256 0zM256 400c-18 0-32-14-32-32s13.1-32 32-32c17.1 0 32 14 32 32S273.1 400 256 400zM325.1 258L280 286V288c0 13-11 24-24 24S232 301 232 288V272c0-8 4-16 12-21l57-34C308 213 312 206 312 198C312 186 301.1 176 289.1 176h-51.1C225.1 176 216 186 216 198c0 13-11 24-24 24s-24-11-24-24C168 159 199 128 237.1 128h51.1C329 128 360 159 360 198C360 222 347 245 325.1 258z"
                  />
                </svg>
              </div>
            </div>
          </div>
          <div
            id="chat-rooms-container"
            class="
              w-64
              bg-slate-500
              container
              mx-auto
              top-0
              bottom-0
              left-16
              absolute
            "
          >
            <div
              id="channel-message-container"
              class="w-full h-48 top-0 left-0 absolute bg-slate-700"
            >
              <h1
                id="title"
                class="
                  w-full
                  font-bold font-raleway
                  top-4
                  text-2xl
                  left-0
                  absolute
                  text-white
                  select-none
                "
              >
                Friends
              </h1>
              <hr
                class="
                  w-full
                  bg-slate-500
                  top-16
                  h-0.5
                  left-0
                  absolute
                  border-none
                "
              />
              <div
                id="tabs"
                class="flex flex-col space-y-1 top-20 w-full left-0 absolute"
              >
                <div
                  id="direct-messages-tab"
                  class="flex absolute w-full h-10 bg-slate-500 cursor-pointer hover:bg-slate-600"
                >
                  <h1
                    class="
                      flex
                      w-full
                      left-4
                      top-1.5
                      absolute
                      text-xl text-slate-50
                      font-mono
                      select-none
                    "
                  >
                    # Direct Messages
                  </h1>
                </div>
                <div
                  id="friends-tab"
                  class="flex w-full top-10 absolute h-10 bg-slate-500 cursor-pointer hover:bg-slate-600"
                >
                  <h1
                    class="
                      flex
                      left-4
                      top-1.5
                      
                      absolute
                      w-full
                      text-xl text-slate-50
                      font-mono
                      select-none
                    "
                  >
                    # Friends
                  </h1>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </body>
  </html>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 0px;
}
</style>