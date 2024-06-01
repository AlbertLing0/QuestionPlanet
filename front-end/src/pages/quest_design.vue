<template>
	<div :class="['component_root', theme_dark && 'theme_dark']">
		<topbar :themeDark="theme_dark"/>
		<sidebar id="sidebar" :selected_items="[2]" @toggle-theme="toggle_theme"/>
		<div id="quest_items_container">
			<div v-for="(item, ndx) in items" :key="ndx" class="quest_item_card">
				<component :is="item.comp"/>
			</div>
		</div>
	</div>
</template>

<script>
import sidebar from "~/components/side-bar.vue";
import topbar from "~/components/top-bar.vue";
import quest_item_ratio from "~/components/quest_item_ratio.vue";
import quest_item_chkbox from "~/components/quest_item_chkbox.vue";

export default {
	name: "quest_design",
	components: {sidebar, topbar, quest_item_ratio, quest_item_chkbox},
	methods: {
		/** @param {boolean} dark */
		set_theme_color(dark) {
			this.theme_dark = dark;
			localStorage.setItem("theme-color", dark ? "dark" : "light");
		},
		toggle_theme() {
			this.set_theme_color(!this.theme_dark);
		},
	},
	data() {
		return {
			theme_dark: localStorage.getItem("theme-color") === "dark",
			/** @type {{ comp: string; }[]} */
			items: [
				{comp: "quest_item_ratio"},
				{comp: "quest_item_chkbox"}
			]
		};
	}
};
</script>

<style scoped>
@import url("~/assets/style/theme_colors.css");

.component_root {
	margin: 0;
	padding: 0;
	background-color: var(--bg-color);
	width: 100vw;
	height: 100vh;
}

#sidebar {
	position: absolute;
	top: 50%;
	transform: translateY(-50%);
	z-index: 10;
}

#quest_items_container {
	position: absolute;
	left: 50%;
	top: 50%;
	width: min-content;
	height: min-content;
	display: flex;
	flex-direction: column;
	transform: translate(-50%, -50%);
}

.quest_item_card {
	margin: 16px;
	width: 75vw;
	height: 100px;
	background-color: var(--quest-item-card-color);
	border-radius: 16px;
}
</style>
