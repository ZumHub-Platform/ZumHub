module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      fontFamily: {
        'raleway': ['Raleway', 'sans-serif'],
      },
      dropShadow: {
        '3xl': '0 35px 35px rgba(0, 255, 0, 0.5)',
      },
      keyframes: {
        scale: {
          "0%": {
            transform: 'scale(0)'
          },
          "100%": {
            transform: 'scale(1)'
          }
        },
        fadeIn: {
          "0%": {
            opacity: 0
          },
          "100%": {
            opacity: 1
          }
        },
        rollDownSuccess: {
          "0%": {
            height: "0px"
          },
          "100%": {
            height: "350px"
          }
        }
      },
      animation: {
        scale: 'scale forwards ease-out 1s',
        fadeIn: 'fadeIn forwards ease-out 1s',
        rollDownSuccessLogin: 'rollDownSuccess forwards ease-out 1s'
      }
    },
  },
  plugins: [
    require("tailwind-scrollbar"),
  ],
}