<template>
  <body>
  <div class="container">
    <div class="card" v-for="(card, index) in cards" :key="index">
      <div class="content">
        <h2>{{ card.number }}</h2>
        <h3>{{ card.title }}</h3>
        <p>{{ card.description }}</p>
        <a href="#">Read More</a>
      </div>
    </div>
  </div>
  </body>
</template>

<script>

import VanillaTilt from 'vanilla-tilt';

export default {
  name: 'GlassCard',
  data() {
    return {
      cards: [
        { number: '01', title: 'Card One', description: 'Realistic glass card hover effect, realistic glass card hover effect, realistic glass card hover effect.' },
        { number: '02', title: 'Card Two', description: 'Realistic glass card hover effect, realistic glass card hover effect, realistic glass card hover effect.' },
        { number: '03', title: 'Card Three', description: 'Realistic glass card hover effect, realistic glass card hover effect, realistic glass card hover effect, realistic glass card hover effect.' }
      ]
    };
  },
  mounted() {
    VanillaTilt.init(document.querySelectorAll(".card"), {
      max: 15,  // Maximum tilt degree
      speed: 400,  // Speed of the transition
      glare: true,  // Enable glare effect
      "max-glare": 1  // Maximum opacity of the glare
    });
  }
};
</script>

<style lang="scss" scoped>
/* 引入网络字体：Poppins */
//@import url("http://fonts.googleapis.com/css?family=Poppins:200,300,400,500,600,700,800,900&display=swap");

*{
  /* 初始化 */
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  /* 设置字体 */
  //font-family: "Poppins";
}
body{
  /* 100%窗口高度 */
  //min-height: 100vh;
  /* 弹性布局 水平+垂直居中 */
  display: flex;
  justify-content: center;
  align-items: center;
  //background-color: #161626;
}
/* 给背景增加两个渐变圆 */

.container{
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  z-index: 1;
}
.container .card{
  /* 相对定位 */
  position: relative;
  width: 250px;
  height: 350px;
  background-color: rgba(255,255,255,0.1);
  margin: 10px;
  border-radius: 15px;
  /* 阴影 */
  box-shadow: 20px 20px 50px rgba(0,0,0,0.5);
  /* 溢出隐藏 */
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  border-top: 1px solid rgba(255,255,255,0.5);
  border-left: 1px solid rgba(255,255,255,0.5);
  /* 背景模糊 */
  backdrop-filter: blur(5px);
}
.container .card .content{
  padding: 20px;
  text-align: center;
  /* 默认下移+隐藏 */
  transform: translateY(100px);
  opacity: 0;
  transition: 0.5s;
}
.container .card:hover .content{
  /* 鼠标移入，上移+显示 */
  transform: translateY(0);
  opacity: 1;
}
.container .card .content h2{
  position: absolute;
  top: -80px;
  right: 25px;
  font-size: 128px;
  color: rgba(255,255,255,0.05);
}
.container .card .content h3{
  font-size: 28px;
  color: #fff;
}
.container .card .content p{
  font-size: 16px;
  color: #fff;
  font-weight: 300;
  margin: 10px 0 15px 0;
}
.container .card .content a{
  position: relative;
  display: inline-block;
  padding: 8px 20px;
  margin-top: 15px;
  background-color: #fff;
  color: #000;
  text-decoration: none;
  border-radius: 20px;
  font-weight: 500;
  box-shadow: 0 5px 15px rgba(0,0,0,0.2);
}

</style>
