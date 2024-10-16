<template>
  <div class="title">
    欢迎进入点餐系统后台页面
  </div>
</template>

<script>
export default {
  name: "Index",
  data() {
    return {
    };
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    }
  }
};
</script>

<style scoped lang="scss">
.title{
  color: green;
  padding: 30px 0px 0px 60px;
  font-size: 30px;
}
</style>

