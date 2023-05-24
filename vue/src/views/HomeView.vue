<template>
  <div style="height: 100%">
    <el-container style="height: 100%;">
      <el-aside :width="sideWith + 'px'" style="height: 100vh; box-shadow: 2px 0 6px rgb(0 21 41)">
        <el-menu :default-openeds="['1', '3']"
                 style="min-height: 100% ; overflow-x:hidden; border-right-color: transparent"
                 background-color="rgb(48,65,86)"
                 text-color="#fff"
                 :collapse-transition="false"
                 :collapse="isCollapse"
        >
          <div style="height: 60px; line-height: 60px; text-align: center">
            <img src="../assets/logo.png" alt="" style="width: 20px;position: relative; top:5px">
            <b style="color: white; margin-left: 5px" v-show="logoTextShow">管理系统</b>
          </div>
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-message"></i>

            </template>
            <el-menu-item-group class="aside-menu-bar">
              <template slot="title">分组一</template>
              <el-menu-item index="1-1">选项1</el-menu-item>
              <el-menu-item index="1-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="1-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="1-4">
              <template slot="title">选项4</template>
              <el-menu-item index="1-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title"><i class="el-icon-menu"></i><span slot="title">导航二</span></template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="2-1">选项1</el-menu-item>
              <el-menu-item index="2-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="2-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="2-4">
              <template slot="title">选项4</template>
              <el-menu-item index="2-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title"><i class="el-icon-setting"></i><span slot="title">导航三</span></template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="3-1">选项1</el-menu-item>
              <el-menu-item index="3-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="3-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="3-4">
              <template slot="title">选项4</template>
              <el-menu-item index="3-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-container>
        <el-header style="font-size: 12px; border-bottom: 1px solid #ccc; line-height: 60px" class="flex">
          <div style="flex: 1; font-size: 20px">
            <span :class="collapseBtnClass" style="cursor:pointer" @click="collapse"></span>
          </div>
          <el-dropdown style="width: 70px; cursor:pointer">
            <span style="user-select: none">王小虎</span>
            <i class="el-icon-arrow-down" style="margin-left: 5px; user-select: none"></i>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>个人信息</el-dropdown-item>
              <el-dropdown-item>退出</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>

        </el-header>

        <el-main>
          <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>活动管理</el-breadcrumb-item>
            <el-breadcrumb-item>活动列表</el-breadcrumb-item>
            <el-breadcrumb-item>活动详情</el-breadcrumb-item>
          </el-breadcrumb>
          <div style="padding: 10px 0">
            <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search"
                      v-model="username"></el-input>
            <el-input style="width: 200px" placeholder="请输入邮箱" suffix-icon="el-icon-search"
                      class="ml-5" v-model="email"></el-input>
            <el-input style="width: 200px" placeholder="请输入地址" suffix-icon="el-icon-search"
                      class="ml-5" v-model="address"></el-input>
            <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
            <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
          </div>

          <div style="margin: 10px 0">
            <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i>
            </el-button>
            <el-popconfirm
                class="ml-5"
                confirm-button-text='好的'
                cancel-button-text='不用了'
                icon="el-icon-info"
                icon-color="red"
                title="确定批量删除删除这些数据吗？"
                @confirm="delBatch"
            >
              <el-button type="danger" slot="reference">批量删除 <i class="el-icon-remove-outline"></i></el-button>
            </el-popconfirm>
            <el-button type="primary" class="ml-5">导入 <i class="el-icon-bottom"></i></el-button>
            <el-button type="primary">导出 <i class="el-icon-top"></i></el-button>

          </div>

          <el-table :data="tableData" border stripe @selection-change="handleSelectionChange">
            <el-table-column
                type="selection"
                width="55">
            </el-table-column>
            <el-table-column prop="id" label="ID" width="80">
            </el-table-column>
            <el-table-column prop="username" label="用户名" width="100">
            </el-table-column>
            <el-table-column prop="nickname" label="昵称" width="120">
            </el-table-column>
            <el-table-column prop="address" label="地址">
            </el-table-column>
            <el-table-column prop="email" label="邮箱">
            </el-table-column>
            <el-table-column prop="phone" label="电话">
            </el-table-column>
            <el-table-column>
              <template slot-scope="scope" label="操作">
                <el-button type="primary" @click="handleEdit(scope.row)"><i class="el-icon-edit"></i></el-button>
                <el-popconfirm
                    class="ml-5"
                    confirm-button-text='好的'
                    cancel-button-text='不用了'
                    icon="el-icon-info"
                    icon-color="red"
                    title="确定删除吗？"
                    @confirm="del(scope.row.id)"
                >
                  <el-button type="danger" slot="reference"><i class="el-icon-remove-outline"></i></el-button>
                </el-popconfirm>

              </template>
            </el-table-column>
          </el-table>
          <div style="padding: 10px 0">
            <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="pageNum"
                :page-sizes="[2, 5, 10, 20]"
                :page-size="pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>

          </div>

          <!-- Form -->
          <el-dialog title="新增" :visible.sync="dialogFormVisible" width="30%">
            <el-form :model="form" label-width="80px">
              <el-form-item label="用户名">
                <el-input v-model="form.username" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="昵称">
                <el-input v-model="form.nickname" autocomplete="off"></el-input>
                <!--                <el-select v-model="form.nickname" placeholder="请选择活动区域">-->
                <!--                  <el-option label="区域一" value="shanghai"></el-option>-->
                <!--                  <el-option label="区域二" value="beijing"></el-option>-->
                <!--                </el-select>-->
              </el-form-item>
              <el-form-item label="地址">
                <el-input v-model="form.address" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="邮箱">
                <el-input v-model="form.email" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="电话">
                <el-input v-model="form.phone" autocomplete="off"></el-input>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="save">确 定</el-button>
            </div>
          </el-dialog>

        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
// @ is an alias to /src

import collapse from "element-ui/packages/collapse";
import request from "@/utils/request";

export default {
  name: 'HomeView',
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      username: "",
      email: "",
      address: "",
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWith: 200,
      logoTextShow: true,
      dialogFormVisible: false,  // 对话框
      form: {},
      multipleSelection: []
    }
  },
  created() {
    // 请求分页查询数据
    this.load()
  },
  methods: {
    collapse() { //点击收缩按钮触发
      this.isCollapse = !this.isCollapse
      if (this.isCollapse) {
        this.sideWith = 64
        this.logoTextShow = false
        this.collapseBtnClass = "el-icon-s-unfold"
      } else {
        this.sideWith = 200
        this.logoTextShow = true
        this.collapseBtnClass = "el-icon-s-fold"
      }
    },
    load(pageNum = this.pageNum) {
      request.get("/user/page", {
        params: {
          pageNum: pageNum,
          pageSize: this.pageSize,
          username: this.username,
          email: this.email,
          address: this.address
        }
      }).then(res => {
        console.log(res)

        this.tableData = res.records
        this.total = res.total

      })
      // fetch("http://localhost:8080/user/page?pageNum=" + this.pageNum + "&pageSize=" + this.pageSize + "&username=" + this.username)
      //     .then(res => res.json()).then(res => {
      //   console.log(res)
      //   this.tableData = res.data
      //   this.total = res.total
      // })
    },
    save() {
      request.post("/user", this.form).then(res => {
        if (res) {
          this.$message.success("Success")
          this.dialogFormVisible = false
          let pageNumber = Math.floor(this.total / this.pageSize) + 1
          this.load(pageNumber)
        } else {
          this.$message.error("Error")
        }
      })
    },
    del(id) {
      request.delete("/user/" + id).then(res => {
        if (res) {
          this.$message.success("Delete success")
          this.load()
        } else {
          this.$message.error("Error")
        }
      })
    },
    reset() {
      this.username = ""
      this.email = ""
      this.address = ""
      this.load()
    },
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },
    delBatch() {
      let ids = this.multipleSelection.map(v => v.id)  //[{}, {}, {}] => [1, 2, 3]
      request.post("/user/del/batch", ids).then(res => {
        if (res) {
          this.$message.success("Delete batch success")
          this.load()
        } else {
          this.$message.error("Error")
        }
      })
    },
    handleSizeChange(pageSize) {
      console.log(pageSize)
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum)
      this.pageNum = pageNum
      this.load()
    },
    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true
    }
  }
}
</script>

<style lang="scss">
$aside-menu-color: rgb(48, 65, 86);
.el-table th {
  background-color: #eeeeee !important;
  color: black;
  font-weight: bold;
}

.aside-menu-bar {
  background-color: $aside-menu-color;
}
</style>
