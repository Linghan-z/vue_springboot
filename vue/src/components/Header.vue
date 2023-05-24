<template>
  <div style="line-height: 60px" class="flex">
    <div style="flex: 1; font-size: 20px">
      <span :class="collapseBtnClass" style="cursor:pointer; font-size: 18px" @click="collapse"></span>
      <el-breadcrumb separator-class="el-icon-arrow-right" style="display: inline-block; margin-left: 10px">
<!--        <el-breadcrumb-item :to="'/'">Home</el-breadcrumb-item>-->
        <el-breadcrumb-item>管理系统</el-breadcrumb-item>
        <el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-dropdown style="width: 70px; cursor:pointer">
      <span style="user-select: none">王小虎</span>
      <i class="el-icon-arrow-down" style="margin-left: 5px; user-select: none"></i>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item>个人信息</el-dropdown-item>
        <el-dropdown-item>退出</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "Header",
  props: {
    collapseBtnClass: String,
    collapse: '',
  },
  computed: {
    currentPathName () {
      return this.$store.state.currentPathName;  //要监听的数据
    }
  },
  watch: {  //监听路由变化
    currentPathName (newVal, oldVal) {
      console.log(newVal)
    }
  }
}
</script>

<style scoped>

</style>