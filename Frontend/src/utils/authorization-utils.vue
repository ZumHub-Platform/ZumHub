<script>
import functions from "../utils/functions.vue";

export default {
  methods: {
    setActive(toActive, toUnactive) {
      functions.methods.removeFromClassList([toActive], "bg-slate-200");
      functions.methods.addToClassList([toActive], "bg-amber-300");
      functions.methods.addToClassList([toUnactive], "bg-slate-200");
      functions.methods.removeFromClassList([toUnactive], "bg-amber-300");
    },
  },
};
</script>