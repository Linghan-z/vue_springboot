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
    <el-dropdown style="width: 100px; cursor:pointer">
      <div>
        <img :src="user.avatarUrl" alt=""
             style="width: 30px; border-radius: 50%; position: relative;top: 10px;right: 5px">
        <span style="user-select: none">{{ user.nickname }}</span><i class="el-icon-arrow-down" style="margin-left: 5px; user-select: none"></i>
      </div>
      <el-dropdown-menu slot="dropdown" style="width: 100px; text-align: center">
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <router-link to="/person" style="text-decoration: none">个人信息</router-link>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <span style="text-decoration: none" @click="logout" >退出</span></el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "Header",
  data() {
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
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
  },
  methods: {
    // collapse() {
    //   this.$emit("asideCollapse")
    // },
    logout() {
      this.$router.push("/login")
      localStorage.removeItem("user")
      this.$message.success("退出成功")
    }
  }
}
</script>

<style scoped>

</style>