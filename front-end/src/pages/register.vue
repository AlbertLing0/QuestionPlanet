<script>
import {defineComponent} from "vue";
import { useRouter } from 'vue-router';
import PlanetBG from "~/components/planetBG.vue";
import NavBar from "~/components/navigation-bar.vue";
import axios from 'axios';

export default defineComponent({
  components: {PlanetBG,NavBar,axios},
  data() {
    return {
      Email: '',
      UserName: '',
      Password: '',
      ConfirmPassword: '',
      EmailVerificationCode: ''
    };
  },
  methods: {
    async sendVerificationCode() {
      const params = new URLSearchParams();
      params.append('email', this.Email);
      try {
        const response = await axios.post('http://localhost:1234/api/sendemail', params);

        // 处理成功响应，例如：
        if (response.data === 0) {
          console.log('验证码发送成功');
          // 可以在这里添加成功提示，例如弹出一个提示框
        } else {
          console.error('验证码发送失败', response);
          // 处理其他错误状态码，例如弹出一个错误提示框
        }
      } catch (error) {
        console.error('发送验证码时出现错误', error);
        // 处理请求错误，例如网络错误，弹出一个错误提示框
      }
    },
    async register() {
      const params = new URLSearchParams();
      const router = useRouter();
      params.append('email', this.Email);
      params.append('username', this.UserName);
      params.append('password', this.Password);
      params.append('code', this.EmailVerificationCode);
      try {
        const response = await axios.post('http://localhost:1234/api/register', params);

        // 处理成功响应，例如：
        if (response.data === 'success') {
          alert('注册成功');
          console.log('注册成功');
          router.push('/');
        } else {
          alert('注册失败');
          console.error('注册失败', response);

        }
      } catch (error) {
        console.error('注册时出现错误', error);

      }
    }
  }
})
</script>

<template>
  <div id="naviBar">
    <nav-bar/>
  </div>
  <div id="BG">
    <planet-b-g/>
  </div>
  <div class="registerBox">
    <div class="title">
      <div class="title-h1">
        Register
      </div>
      <div class="tips">
        Join QuestionPlanet and start your journey!
      </div>
    </div>
    <div class="form">
      <div class="input-wrapper">
        <input type="text" placeholder="UserName" v-model="UserName">
      </div>
      <div class="input-wrapper">
        <input type="password" placeholder="Password" v-model="Password">
      </div>
      <div class="input-wrapper">
        <input type="password" placeholder="Confirm Password" v-model="ConfirmPassword">
      </div>
      <div class="input-wrapper">
        <input type="email" placeholder="Email" v-model="Email">
        <button class="send-code-btn" @click="sendVerificationCode">Send Code</button>
      </div>
      <div class="input-wrapper">
        <input type="text" placeholder="Email Verification Code" v-model="EmailVerificationCode">
      </div>
      <button class="btn" @click ="register">Register</button>
    </div>
    <div class="login">
      Already have an account? <span><router-link to="/login">Login</router-link></span>
    </div>
  </div>
</template>


<style scoped>
#BG {
  position: relative;
  width: 100%;
  height: 100vh;
  overflow: hidden;
}
#naviBar {
  position: absolute;
  width: 100%;
  z-index: 20;
}
@font-face {
  font-family: PlastoTrial_ExtraLight;
  font-weight: 700;
  src: url(src/assets/font/PlastoTrial-ExtraLight.otf) format("truetype");
  text-rendering: optimizeLegibility;
}

@font-face {
  font-family: PlastoTrial_ExtraBold;
  font-weight: 700;
  src: url(src/assets/font/PlastoTrial-ExtraBold.otf) format("truetype");
  text-rendering: optimizeLegibility;
}
@font-face {
  font-family: Marmelad_Regular;
  font-weight: normal;
  src: url(src/assets/font/Marmelad-Regular.ttf) format("truetype");
  text-rendering: optimizeLegibility;
}
.registerBox {
  position: absolute; /* Ensure it can be controlled by z-index */
  z-index: 10; /* Higher than other elements */
  width: 50%;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 5% 10%;
  box-sizing: border-box;
  font-family: PlastoTrial_ExtraLight;
  background: transparent;
  border: 2px solid rgba(255, 255, 255, 0.5);
  backdrop-filter: blur(7px);
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  .title {
    text-align: center;
    .title-h1 {
      margin: 5% 0;
      text-align: center;
      font-size: 3vw;
      font-family: PlastoTrial_ExtraBold;
      color: white;
    }
    .tips {
      line-height: 30px;
      color: #727272;
    }
  }
  .form {
    width: 100%;
    margin-top: 10%;
    .input-wrapper {
      width: 100%;
      margin: 10px 0;
      display: flex;
      align-items: center;
      input {
        flex: 1;
        height: 6vh;
        border: 1px solid #000;
        border-radius: 50px;
        box-sizing: border-box;
        padding: 10px 20px;
        font-size: 20px;
        outline: none;
        font-family: Marmelad_Regular;
      }
      .send-code-btn {
        height: 6vh;
        margin-left: 10px;
        border: 1px solid #000;
        border-radius: 50px;
        background-color: #000;
        color: #fff;
        padding: 0 20px;
        font-size: 16px;
        cursor: pointer;
        font-family: Marmelad_Regular;
      }
    }
    .btn {
      width: 100%;
      height: 50px;
      border: 0;
      background-color: #000;
      color: #fff;
      margin: 10px 0;
      border-radius: 50px;
      font-size: 20px;
      cursor: pointer;
      font-family: PlastoTrial_ExtraBold;
    }
  }
  .login {
    width: 100%;
    text-align: center;
    margin-top: 5%;
    color: white;
    span {
      color: rgb(135, 228, 89);
      cursor: pointer;
    }
  }
}
</style>
