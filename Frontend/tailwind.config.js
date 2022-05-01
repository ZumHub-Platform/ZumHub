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
      }
    },
  },
  plugins: [
    //require("tailwind-scrollbar"),
  ],
}