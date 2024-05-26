<template>
  <div :class="['menu-wrapper', 'component-root', themeDark && 'theme-color-dark']">
    <div class="menu-title">
      <img src="../assets/img/logo/logo-black.svg" alt="" class="logo">
    </div>
    <div class="content">
      <div class="person-info">
        <img src="../assets/head_portrait.jpg" alt="" />
        <div class="person-name">
          <div class="name">MAOS</div>
          <span class="detail">前端小白</span>
        </div>
      </div>
      <div class="menu-content">
        <div class="menu-list">
          <div class="menu-list-item" v-for="item in menuData" :key="item.id">
            <div class="block"></div>
            <span class="iconfont" :class="item.iconFont"></span>
            <div class="item-name">{{ item.menuName }}</div>
          </div>
          <div class="menu-list-item">
            <div class="block"></div>
            <span class="iconfont icon-yueliang"></span>
            <div class="item-name">Dark Mode</div>
            <div class="btn">
              <input class="check-ipt" type="checkbox" id="checks" @change="changeDark" />
              <label class="check-lable" for="checks"></label>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      menuData: [
        { id: 1, menuName: "Dashboard", iconFont: "icon-caidan" },
        { id: 2, menuName: "Products", iconFont: "icon-gouwu" },
        { id: 3, menuName: "Categories", iconFont: "icon-category" },
        { id: 4, menuName: "Orders", iconFont: "icon-order-fill" },
        { id: 5, menuName: "Customers", iconFont: "icon-denglu-yonghuzu" },
        { id: 6, menuName: "Sales Offers", iconFont: "icon-liwu" },
        { id: 7, menuName: "Dealership", iconFont: "icon-bingtu" },
        { id: 8, menuName: "Locations", iconFont: "icon-weizhi" },
        { id: 9, menuName: "Settings", iconFont: "icon-shezhi" },
        { id: 10, menuName: "Logout", iconFont: "icon-jinru" }
      ],
      themeDark: false
    };
  },
  mounted() {
    const savedTheme = localStorage.getItem('theme-color');
    if (savedTheme) {
      this.themeDark = savedTheme === 'dark';
    }
  },
  methods: {
    changeDark() {
      this.themeDark = !this.themeDark;
      localStorage.setItem('theme-color', this.themeDark ? "dark" : "light");
    }
  }
};
</script>

<style lang='scss' scoped>
@import url(../assets/iconfont/iconfont.css);

div.component-root {

  --theme-text-color: rgb(131, 128, 155);
  --theme-info-text-color: rgb(0, 0, 0);
  --theme-hover-color: rgb(103, 56, 223);
  --theme-hover-menu-color: rgb(248, 247, 255);
  --bg-color: rgb(255, 255, 255);
}


div.component-root.theme-color-dark {
  --theme-text-color: rgb(141, 139, 164);
  --theme-info-text-color: rgb(255, 255, 255);
  --theme-hover-color: rgb(255, 255, 255);
  --theme-hover-menu-color: rgb(36, 31, 53);
  --bg-color: rgb(31, 30, 38);
}

.menu-wrapper {
  width: 90px;
  border-radius: 20px;
  background-color: var(--bg-color);
  padding: 20px;
  box-sizing: border-box;
  transition: 0.6s;
  overflow: hidden;
}

.menu-title {
  padding-bottom: 20px;
  box-sizing: border-box;
  // border-bottom: 1px solid rgb(229, 233, 236);
  margin-bottom: 20px;
  .title-text {
    margin-left: 10px;
    font-family: "BasketBall";
    font-size: 30px;
    vertical-align: middle;
    opacity: 0;
    transition: 0.6s;
    color: var(--theme-info-text-color);
  }
  .icon-24gf-bag {
    font-size: 30px;
    padding-left: 10px;
    color: rgb(103, 56, 223);
  }
}

.content .person-info {
  margin-top: 20px;
  white-space: nowrap;
  img {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    border: 2px solid #fff;
    box-shadow: 0px 0px 10px 0px rgba(0, 0, 0, 0.2);
    vertical-align: middle;
  }
  .person-name {
    margin-left: 15px;
    vertical-align: middle;
    opacity: 0;
    transition: 0.6s;
    overflow: hidden;
    color: var(--theme-info-text-color);
    display: inline-block;
    .name {
      font-size: 20px;
      font-weight: 600;
    }
    .detail {
      font-size: 12px;
    }
  }
}

.menu-content .menu-list .menu-list-item {
  cursor: pointer;
  width: 100%;
  height: 50px;
  font-size: 18px;
  position: relative;
  border-radius: 10px;
  padding-left: 10px;
  white-space: nowrap;
  .block {
    width: 6px;
    height: 25px;
    background: rgb(101, 57, 225);
    position: absolute;
    right: -10px;
    top: 13px;
    transition: 0.5s;
    border-top-left-radius: 4px;
    border-bottom-left-radius: 4px;
    opacity: 0;
  }
  .item-name {
    line-height: 50px;
    display: inline-block;
    margin-left: 10px;
    font-size: 18px;
    color: var(--theme-text-color);
    font-weight: 100;
    transition: 0.6s;
    opacity: 0;
  }
  &:hover {
    background-color: var(--theme-hover-menu-color);
    .item-name {
      color: var(--theme-hover-color);
    }
    .iconfont {
      color: var(--theme-hover-color);
    }
    .block {
      opacity: 1;
    }
  }
  .btn {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    display: inline-block;
    opacity: 0;
    margin-left: 10px;
    .check-ipt {
      display: none;
    }
    .check-lable {
      display: inline-block;
      width: 40px;
      height: 20px;
      border-radius: 20px;
      background: rgb(101, 58, 223);
      cursor: pointer;
      position: relative;
      overflow: hidden;
    }
    .check-lable::before {
      display: inline-block;
      content: "";
      width: 15px;
      height: 15px;
      border-radius: 50%;
      background: white;
      position: absolute;
      left: 3px;
      top: 50%;
      transform: translateY(-50%);
      transition: all 0.3s;
    }
    .check-ipt:checked + .check-lable::before {
      left: 20px;
      background: rgb(32, 30, 43);
    }
  }
}

.menu-wrapper:hover {
  width: 250px;
  .menu-title .title-text {
    opacity: 1;
  }
  .content .person-info .person-name {
    opacity: 1;
  }
  .menu-content .menu-list .menu-list-item .item-name,
  .menu-content .menu-list .menu-list-item .btn {
    opacity: 1;
  }
}
</style>
