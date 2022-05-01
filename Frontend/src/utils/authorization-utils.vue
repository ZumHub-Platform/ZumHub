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
    //Adds warning to input, when it's empty
    fieldIsNull(container) {
      functions.methods.changeText(
        "error-message",
        "You must fill all required fields."
      );
      functions.methods.addToClassList([container], "warning");
    },
    //Adds warning to input when it's too short
    fieldIsUnderMinimum(container, minimum) {
      functions.methods.changeText(
        "error-message",
        `This field must have more than ${minimum} characters`
      );
      functions.methods.addToClassList([container], "warning");
    },
    //Adds warning to input when it's too long
    fieldIsOverMaximum(container, maximum) {
      functions.methods.changeText(
        "error-message",
        `This field must have less than ${maximum} characters`
      );
      functions.methods.addToClassList([container], "warning");
    },
    //Adds warning to input when password repeat doesn't match to password and reverse
    passwordDoesntMatch(container) {
      functions.methods.changeText("error-message", "Password doesn't match.");
      functions.methods.addToClassList([container], "warning");
    },
    //Adds warning to input when email has wrong syntax
    wrongEmailSyntax(container) {
      functions.methods.changeText("error-message", "Invalid email.");
      functions.methods.addToClassList([container], "warning");
    },
    invalidEmail(container) {
      functions.methods.changeText("error-message", "This email doesn't seem to exist.");
      functions.methods.addToClassList([container], "warning");
    },
    wrongPassword(container) {
      functions.methods.changeText("error-message", "Wrong password");
      functions.methods.addToClassList([container], "warning");
    },
    userAlreadyExists(container) {
      functions.methods.changeText("error-message", "User already exists, try another email.");
      functions.methods.addToClassList([container], "warning");
    }
  },
};
</script>