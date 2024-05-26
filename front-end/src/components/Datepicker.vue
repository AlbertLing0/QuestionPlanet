<template>
	<div class="date-container">
		<header>
			<p class="date-title">
				<span class="left-arrow" @click="getDates(-1)"></span>
				<span v-text="`${year}年 ${month}月`"></span>
				<span class="right-arrow" @click="getDates(1)"></span>
			</p>
			<p class="week-title">
				<ul>
					<li>Mon</li>
					<li>Tue</li>
					<li>Wed</li>
					<li>Thu</li>
					<li>Fri</li>
					<li>Sat</li>
					<li>Sun</li>
				</ul>
			</p>
		</header>
		<div class="date-content">
			<ul  v-for=" week in dates.weeks">
				<template v-for="day in week">
					<li v-if="day.month === month" class="selectable" @click="returnDate(day.showDate)">{{ day.showDate }}</li>
					<li v-else>{{ day.showDate }}</li>
				</template>
			</ul>
		</div>
	</div>
</template>

<script>
	import data from '../components/designData/data.js';

	export default {
		name: 'Datepicker',
		data() {
			return {
				dates: [],
				month: undefined,
				year: undefined
			}
		},

		mounted() {
			this.dates = data.date();
			this.year = this.dates.year;
			this.month = this.dates.month;
		},

		methods: {
			getDates(n) {
				let year = this.year;
				let month = this.month - 1 + n;

				if (month < 0) {
					year--;
					month = 11;
				}
				if (month > 11){
					year++;
					month = 0;
				}

				this.dates = data.date(year, month);
				this.year = this.dates.year;
				this.month = this.dates.month;
			},

			returnDate(day) {
				let date = `${this.year}-${this.formatDate(this.month)}-${this.formatDate(day)}`;
				this.$emit('sendDate', date);
			},

			formatDate(val) {
				return (val < 10) ? '0' + val : val;
			}
		}
	}

</script>

<style scoped lang="scss">
	.date-container {
	width: 24rem;
	height: 24rem;
	font-size: 1.2rem;

	&:before {
		display: block;
		width: 0;
		height: 0;
		margin: 0 auto;
		content: '';

		border: {
			right: .5rem transparent;
			bottom: .5rem orange;
			left: .5rem transparent;
		}

	}

}

header {
	color: white;
	background-color: orange;
}

.date-title {
	display: flex;
	height: 3rem;
	margin: 0 1.5rem;
	font-size: 1.4rem;
	font-weight: 700;
	justify-content: space-between;
	align-items: center;

	.left-arrow, .right-arrow {
		display: block;
		width: 0;
		height: 0;
		cursor: pointer;
		border-top: .5rem solid transparent;
		border-bottom: .5rem solid transparent;
	}

	.left-arrow {
		border-right: .5rem white;
	}

	.right-arrow {
		border-left: .5rem white;
	}

}

.week-title, .date-content {
	ul {
		display: flex;
	}

	li {
		text-align: center;
		flex: 1;
	}

}

.date-content {
	border: {
		right: 1px solid #ccc;
		bottom: 1px solid #ccc;
		left: 1px solid #ccc;
	}

	ul {
		height: 3.2rem;
		line-height: 3.2rem;
		color: #ddd;
		border-bottom: 1px solid #eee;
		background-color: white;
	}

	li {
		&:not(:last-child) {
			border-right: 1px solid #eee;
		}

		&.selectable {
			cursor: pointer;
			color: #666;

			&:hover {
				background-color: orange;
			}

		}

	}

}
</style>


<!-- <template>
	<div class="demo-datetime-picker">
	  <div class="block">
		<span class="demonstration">Default</span>
		<el-date-picker
		  v-model="value1"
		  type="datetime"
		  placeholder="Select date and time"
		/>
	  </div>
	  <div class="block">
		<span class="demonstration">With shortcuts</span>
		<el-date-picker
		  v-model="value2"
		  type="datetime"
		  placeholder="Select date and time"
		  :shortcuts="shortcuts"
		/>
	  </div>
	  <div class="block">
		<span class="demonstration">With default time</span>
		<el-date-picker
		  v-model="value3"
		  type="datetime"
		  placeholder="Select date and time"
		  :default-time="defaultTime"
		/>
	  </div>
	</div>
  </template>
  
  <script lang="ts" setup>
  import { ref } from 'vue'
  
  const value1 = ref('')
  const value2 = ref('')
  const value3 = ref('')
  const defaultTime = new Date(2000, 1, 1, 12, 0, 0)
  
  const shortcuts = [
	{
	  text: 'Today',
	  value: new Date(),
	},
	{
	  text: 'Yesterday',
	  value: () => {
		const date = new Date()
		date.setDate(date.getDate() - 1)
		return date
	  },
	},
	{
	  text: 'A week ago',
	  value: () => {
		const date = new Date()
		date.setDate(date.getDate() - 7)
		return date
	  },
	},
  ]
  </script>
  <style scoped>
  .demo-datetime-picker {
	display: flex;
	width: 100%;
	padding: 0;
	flex-wrap: wrap;
  }
  .demo-datetime-picker .block {
	padding: 30px 0;
	text-align: center;
	border-right: solid 1px var(--el-border-color);
	flex: 1;
  }
  .demo-datetime-picker .block:last-child {
	border-right: none;
  }
  .demo-datetime-picker .demonstration {
	display: block;
	color: var(--el-text-color-secondary);
	font-size: 14px;
	margin-bottom: 20px;
  }
  </style> -->