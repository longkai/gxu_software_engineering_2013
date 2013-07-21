/**
 * 处理时间日期的函数
 * @author longkai (@爱因斯坦的狗)
 * @email im.longkai@gmail.com
 * @date 2013-05-20 (for liuyuejia:-))
 */
var datetime = (function() {
	return {
		relativeTime: function(millis) {
			var now = new Date().getTime();
			var offset = now - millis;

			if (offset < 0) {
				return "未来";
			}
			// 秒杀
			var second = 1000;
			if (offset < second * 10) {
				return "刚刚~";
			}
			// xx秒前
			var minute = 60 * second;
			if (offset < minute) {
				return Math.ceil(offset / second) + "秒前";
			}
			// xx分钟前
			var hour = minute * 60;
			if (offset < hour) {
				return Math.ceil(offset / minute) + "分钟前";
			}
			// xx小时前
			var day = hour * 24;
			if (offset < day) {
				return Math.ceil(offset / hour) + "小时前";
			}
			// xx天前，简单起见，每个月假设都为30天
			var month = day * 30;
			if (offset < month) {
				return Math.ceil(offset / day) + "天前";
			}
			// xx月前
			var year = month * 12;
			if (offset < year) {
				return Math.ceil(offset / month) + "个月前";
			}
			// xx年前
			return Math.ceil(offset / year) + "年前";
		},
		format: function(pattern, timeMills) {
			if (pattern === 'yyyy-MM-dd HH:mm:ss') {
				var date = new Date(timeMills);
				var year = date.getFullYear();
				var month = date.getMonth() + 1;
				var day = date.getDate();
				var hour = date.getHours();
				var minute = date.getMinutes();
				var second = date.getSeconds();

				month = month < 10 ? '0' + month : month;
				day = day < 10 ? '0' + day : day;
				hour = hour < 10 ? '0' + hour : hour;
				minute = minute < 10 ? '0' + minute : minute;
				second = second < 10 ? '0' + second : second;

				return year + '-' + month + '-' + day + ' ' + hour + ':' + minute + ':' + second;
			}
		}
	}
})(window);