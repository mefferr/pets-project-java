/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/index.html", "./src/**/*.{js,jsx}"],
  theme: {
    extend: {},
  },
  plugins: [require("daisyui")],
  daisyui: {
    themes: [
      {
        light: {
          "primary": "#F2CB05",
          "secondary": "#F29F05",
          "accent": "#F28705",
          "neutral": "#267365",
          "error": "#F23030",
        }
      },
    ],
  },
};