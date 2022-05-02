<script>
import functions from "../utils/functions.vue";
let ac = "auth-container-";

export default {
  methods: {
    //Sets first button to active and second to unactive
    setActive(toActive, toUnactive) {
      functions.methods.removeFromClassList([toActive], "bg-slate-200");
      functions.methods.addToClassList([toActive], "bg-amber-300");
      functions.methods.addToClassList([toUnactive], "bg-slate-200");
      functions.methods.removeFromClassList([toUnactive], "bg-amber-300");
    },
    //Removes styling from inputs
    setNormal(container) {
      for (var i = 0; i < container.length; i++) {
        functions.methods.removeFromClassList([ac + container[i]], "warning");
      }
    },
    sendError(message, container) {
      functions.methods.changeText("error-message", message);
      functions.methods.addToClassList([container], "warning");
    },
    //Adds warning to input, when it's empty
    fieldIsNull(container) {
      this.sendError("You must fill all required fields.", container);
    },
    fieldIsUnderMinimum(container, minimum) {
      this.sendError(
        `This field must have more than ${minimum} characters.`,
        container
      );
    },
    fieldIsOverMaximum(container, maximum) {
      this.sendError(
        `This field must have less than ${maximum} characters.`,
        container
      );
    },
    passwordDoesntMatch(container) {
      this.sendError("Password doesn't match.", container);
    },
    wrongEmailSyntax(container) {
      this.sendError("Invalid email.", container);
    },
    invalidEmail(container) {
      this.sendError("This email doesn't seem to exist.", container);
    },
    wrongPassword(container) {
      this.sendError("Wrong password.", container);
    },
    userAlreadyExists(container) {
      this.sendError("User already exists, try another email.", container);
    },
  },
};
</script>