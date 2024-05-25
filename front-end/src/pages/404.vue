<template>
  <div class="content">
    <div class="page-404">
      <h1>404</h1>
      <p>You are lost this page</p>
      <p class="info">
        You've reached the edge of the universe. The page you've requested can't be found. Don't worry, you can
        return to the previous page.
      </p>
      <div class="buttons">
        <a :href="'#/'"> <button class="go-home btn">GO HOME</button></a>
        <button @click="goBack" class="back btn">BACK</button>
      </div>
    </div>
    <div class="astronaut">
      <img src="../assets/img/404/astronaut.png" class="astronaut-img" alt="astronaut">
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
const router = useRouter();
const goBack = () => {
  router.go(-1); // 返回上一个页面
};
</script>

<style scoped>
@font-face {
  font-family: dogica;
  src: url(src/assets/font/dogica.ttf);
}
* {
  padding: 0;
  margin: 0;
}
.content {
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  background: url(src/assets/img/404/bg.png);
  background-attachment: fixed;
  background-size: cover;
}
.astronaut {
  transform: rotate(0deg) translate(0, -18%);
  animation-name: PAnimation;
  animation-direction: alternate;
  animation-timing-function: linear;
  animation-iteration-count: infinite;
  animation-duration: 3s;
}
.page-404 {
  width: 40vw;
  font-family: dogica;
  z-index: 1;
}
.page-404 h1 {
  font-size: 5vw;
  margin-bottom: 30px;
}
.page-404 p {
  font-size: 1vw;
  margin: 10px 0;
  line-height: 2vw;
}
.page-404 .buttons{
  display: flex;
  gap: 10px;
}
.page-404 .btn {
  width: 10vw;
  height: 50px;
  border-radius: 30px;
  margin-top: 10px;
  margin-right: 20px;
  cursor: pointer;
  transition: 0.3s;
  background-color: #fff;
  color: #000;
  border: 2px solid #000;
  font-size: 1vw;
}
.page-404 .btn:hover {
  background-color: #000;
  color: #fff;
}

@media (min-width: 1024px) {
  .content {
    position: relative;
    display: flex;
  }
  .astronaut {
    position: absolute;
    left: 50%;
    top: 50%;
  }
  .page-404 {
    width: 40vw;
    position: absolute;
    left: 10%;
    top: 40%;
    transform: translateY(-50%);
  }
}

@media (max-width: 1024px) {
  .content {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }
  .page-404 {
    width: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    margin-top: 10vh;
  }
  .page-404 h1 {
    font-size: 10vw;
    margin-bottom: 30px;
  }
  .page-404 p {
    font-size: 3vw;
    line-height: 6vw;
  }
  .page-404 .info {
    display: none;
  }
  .page-404 .btn {
    width: 30vw;
  }
  .astronaut {
    width: 100%;
    display: flex;
    justify-content: center;
  }
  .astronaut-img {
    width: 70%;
    height: 80%;
    transform: translateY(40%);
  }
}

@keyframes PAnimation {
  0% {
    transform: rotate(0deg) translate(0, -15%);
  }
  100% {
    transform: rotate(10deg) translate(0, -18%);
  }
}
</style>
