<template>
    <div class="container">
        <h1>{{ message }}</h1>
        <p>页面将在3秒后自动跳转...</p>
    </div>
</template>
  
<script>
    export default {
        data() {
            return {
                message: ''
            };
            },
        created() {
            // 从查询参数中获取信息
            this.message = this.$route.query.message || this.generateMessage();
            },


        methods: {
            // 定义一个函数来生成显示的文字
            generateMessage() {
            // 这里可以是任何逻辑来生成文字
            return '欢迎来到自动跳转页面！';
            },
            // 跳转函数
            redirectToHome() {
            this.$router.push('/about'); // 假设跳转到首页
            }
        },

        mounted() {
            // 设置定时器，3秒后执行跳转函数
            this.timer = setTimeout(this.redirectToHome, 3000);
        },

        beforeDestroy() {
            // 在组件销毁前清除定时器
            if (this.timer) {
            clearTimeout(this.timer);
            }
        }
    };
</script>

<style scoped>
/* 全局字体设置 */
body {
  font-family: 'Arial', sans-serif;
  font-size: 16px;
  line-height: 1.6;
  color: #333;
  margin: 10px;
  padding: 0;
  background: #f4f4f4;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

/* 容器样式，用于居中内容 */
.container {
  max-width: 800px;
  width: 100%;
  padding: 20px;
  text-align: center;
  background: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
}

/* 标题样式 */
h1 {
  font-size: 2.5em; /* 40px */
  color: #007bff;
  margin-bottom: 0.5em;
}

/* 段落样式 */
p {
  font-size: 1em;
  color: #666;
  margin-top: 0;
}

/* 为跳转信息添加渐变效果 */
@keyframes fadeOut {
  from { opacity: 1; }
  to { opacity: 0; }
}

p {
  animation: fadeOut 3s forwards; /* 3秒后淡出 */
}

/* 响应式设计 */
@media (max-width: 768px) {
  body {
    flex-direction: column;
  }

  .container {
    margin: 20px;
  }
}
</style>