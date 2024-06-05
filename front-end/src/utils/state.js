// src/state.js
import { ref } from 'vue';

export const useSideBarState = () => {
    const isExpanded = ref(false);

    const expandSideBar = () => {
        isExpanded.value = true;
    };

    const collapseSideBar = () => {
        isExpanded.value = false;
    };

    return {
        isExpanded,
        expandSideBar,
        collapseSideBar
    };
};
