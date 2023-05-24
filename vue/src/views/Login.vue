<template>
  <div class="login">
    <el-form ref="loginForm" class="login-form">
      <h3 class="title">管理系统</h3>
      <el-form-item prop="username">
        <el-input
            v-model="user.username"
            type="text"
            auto-complete="off"
            placeholder="账号"
            prefix-icon="el-icon-user"
        >
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
            v-model="user.password"
            type="password"
            auto-complete="off"
            placeholder="密码"
            prefix-icon="el-icon-lock"
            @keyup.enter.native="handleLogin"
        >
        </el-input>
      </el-form-item>
      <el-form-item style="width:100%;">
        <el-button
            size="medium"
            type="primary"
            style="width:100%;"
            @click.native.prevent="handleLogin"
        >
          <span>登 录</span>
        </el-button>

      </el-form-item>
    </el-form>
    <!--  底部  -->
    <div class="el-login-footer">
      <span>Copyright ©...</span>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      user: {}
    }
  },
  methods: {
    handleLogin(){

    }
  }
}
</script>

<style lang="scss">
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-image: linear-gradient(to bottom right, #FC466B, #3F5EFB);
  background-size: cover;
}

.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #707070;
}

.login-form {
  border-radius: 6px;
  background: #ffffff;
  width: 400px;
  padding: 25px 25px 5px 25px;

  .el-input {
    height: 38px;

    input {
      height: 38px;
    }
  }

  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}

.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}

.login-code {
  width: 33%;
  height: 38px;
  float: right;

  img {
    cursor: pointer;
    vertical-align: middle;
  }
}

.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}

.login-code-img {
  height: 38px;
}
</style>