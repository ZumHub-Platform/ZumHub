<script>
export default {
  methods: {
    hide(element) {
      for (var i = 0; i < element.length; i++) {
        document.getElementById(element[i]).style.display = "none";
      }
    },
    //Shows all elements that array contains
    show(element) {
      for (var i = 0; i < element.length; i++) {
        document.getElementById(element[i]).style.display = "block";
      }
    },
    changeValue(container, value) {
      document.getElementById(container).value = value;
    },
    //Changes text of element ID
    changeText(container, value) {
      document.getElementById(container).innerText = value;
    },
    resetAnimation(container) {
      var el = document.getElementById(container);
      el.style.animation = "none";
      el.offsetHeight; /* trigger reflow */
      el.style.animation = null;
    },
    addToClassList(container, className) {
      for (var i = 0; i < container.length; i++) {
        var a = document.getElementById(container[i]);
        a.classList.add(className);
      }
    },
    removeFromClassList(container, className) {
      for (var i = 0; i < container.length; i++) {
        var a = document.getElementById(container[i]);
        a.classList.remove(className);
      }
    },
    shrink(container, action) {
      for (var i = 0; i < container.length; i++) {
        if (action === "remove") {
          this.removeFromClassList([container[i]], "shrink");
        } else if (action === "add") {
          this.addToClassList([container[i]], "shrink");
        }
      }
    },
    extend(container, action) {
      for (var i = 0; i < container.length; i++) {
        if (action === "remove") {
          this.removeFromClassList([container[i]], "extend");
        } else if (action === "add") {
          this.addToClassList([container[i]], "extend");
        }
      }
    }
  },
};
</script>

