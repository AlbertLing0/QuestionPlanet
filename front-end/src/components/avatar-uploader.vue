<template>
  <el-upload
      class="avatar-uploader"
      :action="uploadUrl"
      :show-file-list="false"
      :on-success="handleAvatarSuccess"
      :before-upload="beforeAvatarUpload"
      :data="uploadData"
  >
    <img v-if="imageUrl" :src="imageUrl" class="avatar" />
    <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
  </el-upload>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import {POST_UPLOAD_AVATAR} from '~/utils/request'

import type { UploadProps } from 'element-plus'

const imageUrl = ref('')
const uploadData = ref(localStorage.getItem("username"))
const uploadUrl=ref(POST_UPLOAD_AVATAR)

const handleAvatarSuccess: UploadProps['onSuccess'] = (response, uploadFile) => {
  imageUrl.value = URL.createObjectURL(uploadFile.raw!)
}

const beforeAvatarUpload: UploadProps['beforeUpload'] = (rawFile) => {
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
