<template>
  <div class="container">
    <div id="slide">
      <div class="item" v-for="(item, index) in items" :key="index" :style="{backgroundImage:require(item.image)}">
        <div class="content" v-show="index === currentIndex">
          <div class="name">{{ item.name }}</div>
          <div class="des">{{ item.description }}</div>
          <button>See more</button>
        </div>
      </div>
    </div>
    <div class="buttons">
      <button @click="prev"><i class="fa-solid fa-angle-left"></i></button>
      <button @click="next"><i class="fa-solid fa-angle-right"></i></button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      currentIndex: 1,
      items: [
        { image: 'static/test/1.jpg', name: 'LUNDEV', description: 'Tinh ru anh di chay pho, chua kip chay pho thi anhchay mat tieu' },
        { image: 'static/test/2.jpg', name: 'LUNDEV', description: 'Tinh ru anh di chay pho, chua kip chay pho thi anhchay mat tieu' },
        { image: 'static/test/3.jpg', name: 'LUNDEV', description: 'Tinh ru anh di chay pho, chua kip chay pho thi anhchay mat tieu' },
      ],
    };
  },
  methods: {
    next() {
      const nextIndex = this.currentIndex + 1;
      this.currentIndex = nextIndex % this.items.length;
    },
    prev() {
      const prevIndex = this.currentIndex - 1;
      this.currentIndex = prevIndex < 0 ? this.items.length - 1 : prevIndex;
    },
  },
};
</script>

<style scoped>
body{

  overflow: hidden;
}
.container{
  background-color: black;
  position: absolute;
  left:50%;
  top:50%;
  transform: translate(-50%,-50%);
  width:1000px;
  height:600px;
  padding:50px;

  box-shadow: 0 30px 50px #dbdbdb;
}
#slide{
  width:max-content;
  margin-top:50px;
}
.item{
  width:200px;
  height:300px;
  background-position: 50% 50%;
  display: inline-block;
  transition: 0.5s;
  background-size: cover;
  position: absolute;
  z-index: 1;
  top:50%;
  transform: translate(0,-50%);
  border-radius: 20px;
  box-shadow:  0 30px 50px #505050;
}
.item:nth-child(1),
.item:nth-child(2){
  left:0;
  top:0;
  transform: translate(0,0);
  border-radius: 0;
  width:100%;
  height:100%;
  box-shadow: none;
}
.item:nth-child(3){
  left:50%;
}
.item:nth-child(4){
  left:calc(50% + 220px);
}
.item:nth-child(5){
  left:calc(50% + 440px);
}
.item:nth-child(n+6){
  left:calc(50% + 660px);
  opacity: 0;
}
.item .content{
  position: absolute;
  top:50%;
  left:100px;
  width:300px;
  text-align: left;
  padding:0;
  color:#eee;
  transform: translate(0,-50%);
  display: none;
  font-family: system-ui;
}
.item:nth-child(2) .content{
  display: block;
  z-index: 11111;
}
.item .name{
  font-size: 40px;
  font-weight: bold;
  opacity: 0;
  animation:showcontent 1s ease-in-out 1 forwards
}
.item .des{
  margin:20px 0;
  opacity: 0;
  animation:showcontent 1s ease-in-out 0.3s 1 forwards
}
.item button{
  padding:10px 20px;
  border:none;
  opacity: 0;
  animation:showcontent 1s ease-in-out 0.6s 1 forwards
}
@keyframes showcontent{
  from{
    opacity: 0;
    transform: translate(0,100px);
    filter:blur(33px);
  }to{
     opacity: 1;
     transform: translate(0,0);
     filter:blur(0);
   }
}
.buttons{
  position: absolute;
  bottom:30px;
  z-index: 222222;
  text-align: center;
  width:100%;
}
.buttons button{
  width:50px;
  height:50px;
  border-radius: 50%;
  border:1px solid #555;
  transition: 0.5s;
}.buttons button:hover{
   background-color: #bac383;
 }

</style>