<template>
    <div class= "display-bar">
        
        <div class = "display-container">
            <input 
                class= "displayinput"
                :type= "inputtype"
                :placeholder= "displaymessage"
                :disabled = "isdisabled"
                :style="inputStyle" 
                v-model= "inputValue"
            >
        </div>
        <div class="display-info" style="white-space: pre;">{{displayinfo}}</div>
    </div>
</template>

<script setup>
import { ref, onMounted, watch, defineEmits } from 'vue';

const props = defineProps({
    displaymessage: String,
    inputtype: String,
    isdisabled: Boolean,
    displayinfo: String
});
    
const emit = defineEmits(['input-changed']);
const inputValue = ref('');
const containerWidth = ref(0);
const inputStyle = ref({ width: '0em' }); 

const calculateContainerWidth = (message) => {
    // 创建一个临时的 span 元素来测量文本长度
    const tempSpan = document.createElement('span');
    tempSpan.style.position = 'absolute';
    tempSpan.style.visibility = 'hidden';
    tempSpan.style.fontSize = '14px'; // 与输入框字体大小一致
    tempSpan.innerText = message;
    document.body.appendChild(tempSpan);

    // 获取文本宽度并添加边距
    const textWidth = tempSpan.offsetWidth;
    const emWidth = textWidth / 14;
    containerWidth.value = emWidth + 1; // 左右边距

    // 移除临时元素
    document.body.removeChild(tempSpan);

    inputStyle.value = { width: containerWidth.value + 'em' };
};
    
onMounted(() => {
    calculateContainerWidth(props.displaymessage); 
});
    

watch(inputValue, () => {
    if(inputValue.value){
        calculateContainerWidth(inputValue.value); 
    }
    else{
        calculateContainerWidth(props.displaymessage); 
    }
    emit('input-changed', {
        inputvalue: inputValue.value,
        displayinfo: props.displayinfo
    });
});

watch(() => props.displaymessage, () => {
    calculateContainerWidth(props.displaymessage); 
});

</script>

<style scoped>
.display-info {
    font-size: 16x;
    color: #8c939d;
    margin-inline: 1em;
    width: 3em;
}

.display-bar {
    flex-direction: row;
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 90%;
}

.display-container {
    height: 1.5em;
    border: 2px solid var(--el-text-color-placeholder);
    border-radius: 8px;
    display: flex;
    align-items: center;
    justify-content: center;
    width: auto;
    min-width: 18em;
    margin-left: 35%;
}

.displayinput {
    border:0ch;
    margin-left: 1.5em;
    margin-right: 1em;
    font-size: 14px;
    background-color: transparent;
}

.displayinput:focus{
    outline: none;
}
</style>
