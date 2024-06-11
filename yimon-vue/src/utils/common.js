export default {
	getUuid() {
		var s = [];
		var hexDigits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
		for (var i = 0; i < 36; i++) {
			var subIndex01 = Math.floor(Math.random() * 0x10);
			s[i] = hexDigits.substring(subIndex01, subIndex01 + 1)
		}
		s[14] = "4"
		var subIndex02 = (s[19] & 0x3) | 0x8;
		s[19] = hexDigits.substring(subIndex02, subIndex02 + 1)
		s[8] = s[13] = s[18] = s[23] = "-"
		let uuid = s.join("")
		return uuid
	},

	getNowDate() {
		const date = new Date();
		const year = date.getFullYear();
		const month = String(date.getMonth() + 1).padStart(2, '0');
		const day = String(date.getDate()).padStart(2, '0');
		const hours = String(date.getHours()).padStart(2, '0');
		const minutes = String(date.getMinutes()).padStart(2, '0');
		const seconds = String(date.getSeconds()).padStart(2, '0');
		return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
	},
}