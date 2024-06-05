<template>
  <body>
  <div class="container">
    <div v-for="(card, index) in cards" :key="card.id" class="card" v-show="currentIndex === index">
      <div class="img-box">
        <img :src="card.image" alt="">
      </div>
      <div class="text-box">
        <h2>{{ card.title }}</h2>
        <p>{{ card.content }}</p>
      </div>
    </div>
  </div>
  </body>
</template>

<script>
export default {
  data() {
    return {
      currentIndex: 0,
      cards: [
        { id: 1, image: 'static/test/1.jpg', title: '卡片一', content: '我是内容一' },
        { id: 2, image: 'static/test/2.jpg', title: '卡片二', content: '我是内容二' },
        { id: 3, image: 'static/test/3.jpg', title: '卡片三', content: '我是内容三' }
      ]
    };
  },
  mounted() {
    this.startRotation();
  },
  methods: {
    startRotation() {
      setInterval(() => {
        this.currentIndex = (this.currentIndex + 1) % this.cards.length;
      }, 3000); // Rotate every 3000 milliseconds (3 seconds)
    }
  }
};
</script>

<style lang="scss" scoped>
* {
  margin: 0;
  padding: 0;
}

body {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(200deg, #80d0c7, #13547a);
}

.container {
  display: flex;
  flex-wrap: wrap;
  padding: 30px;
}

.card {
  max-width: 300px;
  height: 215px;
  background-color: #fff;
  margin: 30px 15px;
  padding: 20px 15px;
  border-radius: 5px;
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.5);
  display: flex;
  flex-direction: column;
  align-items: center;
  transition: height 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
}

.card:hover {
  height: 420px;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.5);
}

.img-box {
  position: relative;
  width: 50%;
  height: 200px;
  overflow: hidden;
  border-radius: 5px;
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.2);
}

.img-box img {
  width: 100%;
}

.text-box {
  visibility: hidden;
  opacity: 0;
  transition: visibility 0.2s, opacity 0.2s ease-in-out;
  padding: 10px 15px;
  text-align: center;
  color: #111;
}

.card:hover .text-box {
  visibility: visible;
  opacity: 1;
  margin-top: -40px;
}
</style>
