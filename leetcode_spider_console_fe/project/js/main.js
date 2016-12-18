/**
 * Created by junlanli on 12/13/16.
 */
var host = "http://localhost:8080";
var get = true;

Vue.filter('time', function (value) {
    if (value == null || value === '') return "";
    return new Date(parseInt(value)).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
});

Vue.filter('status', function (value) {
    if (value == null || value === '') return "";
    if (value === 0) {
        return "Processing";
    } else if (value === 1) {
        return "Success";
    } else if (value === 2) {
        return "Failed";
    } else {
        return "Unknown Status";
    }
});

var taskTag = new Vue({
    el: '#taskTag',
    data: {
        total: 0,
        list: []
    }
});

var problemTag = new Vue({
   el: '#problemTag',
    data: {
       total: 0,
        list: []
    }
});

var createTask = new Vue({
    el: '#createTask',
    data: {
        Cookie:'PHPSESSID=3352hq5e8l4g22pr0oizfaqnuoc51n8u; _gat=1; csrftoken=UhJsfTNvfCGkK7xFUxqhhWSK1fkLHeY8nqzYJ2gbaiTiEe0p0SDRHxYvQBKYhQ4I; _ga=GA1.2.1845630433.1476512054; __atuvc=56%7C46%2C26%7C47%2C30%7C48%2C38%7C49%2C4%7C50; __atuvs=5850e798797350dc001',
        UserAgent:'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.75 Safari/537.36'
    },
    methods: {
        create: function () {
            var sendData = {};
            sendData.Cookie = this.Cookie;
            sendData.UserAgent = this.UserAgent;
            get = false;
            $.ajax({
                type: 'POST',
                dataType : "JSON",
                contentType: "application/json;charset=utf-8",
                url: host + '/console/problem/start',
                data: JSON.stringify(sendData),
                success: function (data) {
                    if (data.code === 200) {
                        alert("task started");
                        $('#my-modal').modal('hide');
                        get = true;
                        updateTask();
                    } else {
                        alert("list task failed\ncode: " + data.code + "\nmessage: " + data.msg);
                    }
                }
            });
        }
    }
});

var page = new Vue({
    el: '#page',
    data: {
        start: 0,
        end: 10
    },
    computed: {
        canOlder: function () {
            return (this.start > 0);
        },
        canNewer: function () {
            return (this.end < taskTag.total);
        },
        pageNumber: function () {
            return this.start / 10 + 1;
        }
    },
    methods: {
        last: function () {
            if (this.start <= 0) return;
            get = false;
            var s = this.start;
            s -= 10;
            var e = this.end;
            e -= 10;
            if (s < 0) {
                e = e - s;
                s = 0;
            }
            this.start = s;
            this.end = e;
            get = true;
            updateTask();
        },
        next: function () {
            if (this.end >= taskTag.total) return;
            get = false;
            var s = this.start;
            var e = this.end;
            s += 10;
            e += 10;
            this.start = s;
            this.end = e;
            get = true;
            updateTask();
        }
    }
});

var updateTask = function () {
    if (!get) return;
    $.ajax({
        type: 'GET',
        url: host + '/console/task?start=' + page.start + '&end=' + page.end,
        success: function (data) {
            if (data.code === 200) {
                taskTag.total = data.data.total;
                taskTag.list = data.data.list;
                setTimeout(updateTask, 5000);
            } else {
                alert("list task failed\ncode: " + data.code + "\nmessage: " + data.msg);
            }
        }
    });
};

updateTask();

