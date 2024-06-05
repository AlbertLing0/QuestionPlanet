<template>
  <el-upload
      class="avatar-uploader"
      :http-request="uploadAvatar"
      :show-file-list="false"
      :before-upload="beforeAvatarUpload"
  >
    <img v-if="imageUrl" :src="imageUrl" class="avatar" />
    <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
  </el-upload>
</template>

<script>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import axios from 'axios'
import { POST_UPLOAD_AVATAR } from '~/utils/request'

export default {
  name:"avatar-uploader1",
  components:{Plus},
  setup() {
    const imageUrl = ref('')
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
        onSuccess(response.data)
        imageUrl.value = URL.createObjectURL(file)
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
      uploadData,
      uploadAvatar,
      beforeAvatarUpload
    }
  }
}
</script>

<style scoped>
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>

<style>
.avatar-uploader el-upload {
  border: 1px dashed var(--theme-info-text-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader el-upload:hover {
  border-color: var(--el-color-primary);
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>
