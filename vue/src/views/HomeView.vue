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
            <el-input style="width: 200px" placeholder="请输入要搜索的内容" suffix-icon="el-icon-search"
                      v-model="username"></el-input>
<!--            <el-input style="width: 200px" placeholder="请输入要搜索的内容" suffix-icon="el-icon-search"-->
<!--                      class="ml-5"></el-input>-->
<!--            <el-input style="width: 200px" placeholder="请输入要搜索的内容" suffix-icon="el-icon-search"-->
<!--                      class="ml-5"></el-input>-->
            <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
          </div>

          <div style="margin: 10px 0">
            <el-button type="primary">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
            <el-button type="danger">批量删除 <i class="el-icon-remove-outline"></i></el-button>
            <el-button type="primary">导入 <i class="el-icon-bottom"></i></el-button>
            <el-button type="primary">导出 <i class="el-icon-top"></i></el-button>

          </div>

          <el-table :data="tableData" border stripe>
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
                <el-button type="primary"><i class="el-icon-edit"></i></el-button>
                <el-button type="danger"><i class="el-icon-remove-outline"></i></el-button>
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
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
// @ is an alias to /src

import collapse from "element-ui/packages/collapse";

export default {
  name: 'HomeView',
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 2,
      username: "",
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWith: 200,
      logoTextShow: true
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
    load(){
      fetch("http://localhost:8080/user/page?pageNum=" + this.pageNum + "&pageSize=" + this.pageSize + "&username=" + this.username)
          .then(res => res.json()).then(res => {
        console.log(res)
        this.tableData = res.data
        this.total = res.total
      })
    },
    handleSizeChange(pageSize){
      console.log(pageSize)
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum)
      this.pageNum=pageNum
      this.load()
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
