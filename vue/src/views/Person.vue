<template>
  <el-card style="width: 500px; padding: 20px">
    <el-form label-width="80px" size="small">
      <el-form-item label="用户名">
        <el-input v-model = "form.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model = "form.nickname" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model = "form.email" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model = "form.phone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="地址">
        <el-input v-model = "form.address" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="save">确 定</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
export default {
  name: "Person",
  data () {
    return {
      form:{},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.request.get("/user/username/" + this.user.username).then(res =>{
      if (res.code === '200') {
        console.log(res.data)
        this.form = res.data;
        console.log(this.form)
      }
    })
  },
  methods:{
    save() {
      this.request.post("/user", this.form).then(res => {
        if (res.data) {
          this.$message.success("Success")
        } else {
          this.$message.error("Error")
        }
      })
      this.$router.push("/user")
    },
  }
}
</script>

<style scoped>

</style>