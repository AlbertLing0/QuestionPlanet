<template>
  <el-upload
      class="avatar-uploader"
      :http-request="uploadAvatar"
      :show-file-list="false"
      :before-upload="beforeAvatarUpload"
      :class="{ 'upload-success': uploadSuccess }" 
  >
    <img v-if="imageUrl" :src="imageUrl" :style="{ width: 54 * imageWidth / imageHeight + 'px', height: '54px' }" class="avatar" />
    <div class="avatar-uploader-icon-contianer" v-if="!imageUrl">
      <el-icon class="avatar-uploader-icon"><Plus /></el-icon>
    </div>
    
  </el-upload>
  <div class= "remind-text">上传头像</div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import axios from 'axios'
import { POST_UPLOAD_AVATAR } from '~/utils/request'

export default {
  name:"avatar-uploader1",
  components:{Plus},
  props:{
    displaymessage: String,
    inputtype: String,
    isdisabled: Boolean,
    displayinfo: String
  },
  setup() {
    const uploadSuccess = ref(false);
    const imageUrl = ref('');
    const imageWidth = ref(0);
    const imageHeight = ref(0);
    const username = localStorage.getItem("username") || "default_username"
    const uploadData = ref({ username })

    const uploadAvatar = async (options) => {
      const { file, onSuccess, onError } = options
      const formData = new FormData()
      formData.append('username', username)
      formData.append('avatar', file)

      try {
        const response = await axios.post(POST_UPLOAD_AVATAR, formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        })
        onSuccess(response.data);
        imageUrl.value = URL.createObjectURL(file);
        const img = new Image();
        img.onload = () => {
          imageWidth.value = img.width;
          imageHeight.value = img.height;
          uploadSuccess.value = true;
        };
        img.src = imageUrl.value;

      } catch (error) {
        onError(error)
        ElMessage.error('Upload failed!')
      }
    }

    const beforeAvatarUpload = (rawFile) => {
      const isJpegOrPng = rawFile.type === 'image/jpeg' || rawFile.type === 'image/png'
      const isLt5M = rawFile.size / 1024 / 1024 < 5

      if (!isJpegOrPng) {
        ElMessage.error('Only JPG/PNG files are allowed!')
        return false
      }

      if (!isLt5M) {
        ElMessage.error('Avatar picture size cannot exceed 5MB!')
        return false
      }

      return true
    }

    return {
      imageUrl,
      imageWidth,
      imageHeight,
      uploadData,
      uploadAvatar,
      beforeAvatarUpload,
      uploadSuccess
    }
  }
}
</script>

<style scoped>
.avatar-uploader .avatar {
  
  display: block;
  object-fit: contain; /* 图片自适应容器大小 */
  padding-top: 5px;
}

.remind-text {
  font-size: 16px;
  color: var(--theme-text-color);
  margin-right: 10%;
}
</style>

<style>

.avatar-uploader {
  border: 2px solid var(--theme-text-color);
  border-radius: 2px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
  margin-left:41%;
  height: 50px;
  width: 50px;
  display: flex;
}

.avatar-uploader.upload-success {
  display: inline-block;
  width: auto;
  height: auto;
  border-radius: 10px;
}

.avatar-uploader-icon-contianer {
  height: 24px;
  width: 24px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-left: 90%;
}

.avatar-uploader-icon {
  font-size: 18px;
  color: #8c939d;
  text-align: center;
  
}

</style>
