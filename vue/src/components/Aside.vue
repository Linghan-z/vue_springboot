<template>
  <el-menu :default-openeds="['1', '3']"
           style="min-height: 100% ; overflow-x:hidden; border-right-color: transparent"
           background-color="rgb(48,65,86)"
           text-color="#fff"
           :collapse-transition="false"
           :collapse="isCollapse"
           router
  >
    <div style="height: 60px; line-height: 60px; text-align: center">
      <img src="../assets/logo.png" alt="" style="width: 20px;position: relative; top:5px">
      <b style="color: white; margin-left: 5px" v-show="logoTextShow">管理系统</b>
    </div>
    <el-menu-item index="/">
      <i class="el-icon-house"></i><span slot="title">主页</span>
    </el-menu-item>
    <el-submenu index="2">
      <template slot="title"><i class="el-icon-menu"></i><span slot="title">系统管理</span></template>
      <el-menu-item index="/user">
        <template slot="title"><i class="el-icon-s-custom"></i>用户管理</template>
      </el-menu-item>
      <el-submenu index="2-4">
        <template slot="title">选项4</template>
        <el-menu-item index="2-4-1">选项4-1</el-menu-item>
      </el-submenu>
    </el-submenu>
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props: {
    isCollapse: Boolean,
    logoTextShow: Boolean
  }
}
</script>

<style scoped lang="scss">
$aside-menu-color: rgb(48, 65, 86);

.aside-menu-bar {
  background-color: $aside-menu-color;
}
</style>