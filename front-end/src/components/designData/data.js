var date = function(year, month) {
	// 若未传入参数或参数不完整 即获取当月的数据
	if (year === undefined || month === undefined) {
		let today = new Date();
		year = today.getFullYear();
		month = today.getMonth();
	}

	// 获取该月第一天和该天是礼拜几
	var firstDay = new Date(year, month, 1);
	var firstDayWeekday = firstDay.getDay() || 7;

	// 获取该月最后一天日期和该天是礼拜几
	var lastDay = new Date(year, month+1, 0);
	var lastDate = lastDay.getDate();


	// 获取上个月需显示天数和上月最后一天日期
	var lastDayOfPrevMonth = new Date(year, month, 0);
	var lastDateOfPrevMonth = lastDayOfPrevMonth.getDate();
	var prevMonthDayCount = firstDayWeekday - 1; 	 // 日历起始日为星期一
	// var prevMonthDayCount = firstDayWeekday; // 日历起始日为星期日

	var count = 0;
	var dayArr = [];
	var weekArr = [];

	for (let j = 0; j < 6; j++) {
		for (let i = 0;  i < 7; i++) {
			count++;
			let showDate,
			 	showMonth,
			  	date = count - prevMonthDayCount;

			if (date <= 0) {
				// 上个月
				showDate = lastDateOfPrevMonth + date;
				showMonth = month;
			}
			else if (date > lastDate) {
				// 下个月
				showDate = date - lastDate;
				showMonth = month + 2;
			}
			else {
				showDate = date;
				showMonth = month + 1;
			}

			if (showMonth >= 13) showMonth = 1;
			if (showMonth <= 0) showMonth = 12;

			dayArr.push({
				date: date,
				month: showMonth,
				showDate: showDate
			});
		}
		weekArr.push(dayArr.slice());
		dayArr.length = 0;
	}

	// 重新获取当前年月 以确保数据准确
	year = firstDay.getFullYear();
	month = firstDay.getMonth() + 1;

	// 此处的 flag 是用来判断最后一周是不是全部属于下个月 是则移除最后一周
	var flag = weekArr[5].every( item => item.month !== month);
	if (flag) {
		weekArr.pop();
	}

	return {
		year: year,
		month: month,
		weeks: weekArr
	};
};


var template = {
    radio: {
            "type": "radio",
            "topic": "单选题",
            "options": ["选项1","选项2","选项3","选项4"]
        },
    checkbox: {
            "type": "checkbox",
            "topic": "多选题",
            "options": ["选项1","选项2","选项3","选项4"]
        },
    textarea: {
            "type": "textarea",
            "topic": "文本题",
            "isMandatory": false
        }
};


export default {
	template,
	date
}