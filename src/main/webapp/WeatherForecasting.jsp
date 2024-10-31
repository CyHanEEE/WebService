<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>城市天气查询</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        body { font-family: Arial, sans-serif; }
        .container { margin: 20px; }
        select, input { margin: 5px; }
    </style>
</head>
<body>
<div class="container">
    <h2>城市天气查询</h2>
    <label for="locationType">选择城市类型：</label>
    <select id="locationType">
        <option value="">请选择城市类型</option> <!-- 初始选项 -->
        <option value="domestic">国内城市</option>
        <option value="international">国外城市</option>
    </select>

    <label for="province">选择省份/国家：</label>
    <select id="province">
        <!-- 省份选项将通过API动态加载 -->
    </select>

    <label for="city">选择城市：</label>
    <select id="city">
        <option>选择城市</option>
        <!-- 城市选项将根据省份动态加载 -->
    </select>

    <div id="weatherInfo">
        <!-- 天气实况信息将在这里显示 -->
        <h3>当前天气</h3>
        <p>地区：<span id="regionInfo"></span></p>
        <p>城市：<span id="cityName"></span></p>
        <p>最后更新时间：<span id="lastUpdate"></span></p>
        <%--天气实况：--%>
        <p><span id="currentWeatherFacts"></span></p>
        <%--空气质量与紫外线强度：--%>
        <p><span id="airQualityAndUVIntensity"></span></p>
        <%--天气与生活指数：--%>
        <p><span id="weatherAndLivingIndex"></span></p>
    </div>

    <div id="futureWeatherInfo">
        <h3>未来天气情况</h3>
        <table id="futureWeather">
            <thead>
            <tr>
<%--                <th>日期</th>--%>
                <th>天气概况</th>
                <th>气温</th>
                <th>风力/风向</th>
                <th>天气图标1</th>
                <th>天气图标2</th>
            </tr>
            </thead>
            <tbody>
            <!-- 未来天气情况将在这里动态显示 -->
            </tbody>
        </table>
    </div>
</div>

<script>
    $(document).ready(function() {
        $(document).on('change', '#locationType', function() {
            var type = $(this).val();
            var url = type === 'domestic' ? 'api/provinces' : '/api/internationalProvinces';

            // 清空省份和城市下拉框
            $('#province').empty().append('<option value="">请选择省份</option>');
            $('#city').empty().append('<option value="">请选择城市</option>');

            console.log('Fetching from URL:', url); // 检查 URL

            $.ajax({
                url: url,
                type: 'GET',
                dataType: 'json',
                success: function(data) {
                    populateProvinces(data);
                },
                error: function(xhr, status, error) {
                    console.error('Error fetching provinces:', status, error);
                }
            });
        });

        function populateProvinces(provinces) {
            var provinceSelect = $('#province');
            provinceSelect.empty();
            // 添加“选择城市”的默认选项
            provinceSelect.append($('<option></option>').val('').html('选择省份/国家'));
            $.each(provinces, function(index, province) {
                provinceSelect.append($('<option></option>').val(province.id).html(province.name));
            });
        }

        $('#province').change(function() {
            var provinceId = $(this).val();
            $.ajax({
                url: '/api/cities?provinceId=' + provinceId,
                type: 'GET',
                dataType: 'json',
                success: function(data) {
                    populateCities(data);
                },
                error: function(xhr, status, error) {
                    console.error('Error fetching cities:', status, error);
                }
            });
        });

        function populateCities(cities) {
            var citySelect = $('#city');
            citySelect.empty();
            // 添加“选择城市”的默认选项
            citySelect.append($('<option></option>').val('').html('选择城市'));
            $.each(cities, function(index, city) {
                citySelect.append($('<option></option>').val(city.id).html(city.name));
            });
        }

        $('#city').change(function() {
            var cityId = $(this).val();
            // 根据选择的城市调用API获取天气信息
            $.getJSON('/api/weather?cityId=' + cityId, function(data) {
                displayWeatherInfo(data);
            });
        });

        function displayWeatherInfo(weatherData) {
            // 显示当前天气信息
            $('#regionInfo').text(weatherData.regionInfo);
            $('#cityName').text(weatherData.cityName);
            $('#lastUpdate').text(weatherData.lastUpdate);
            $('#currentWeatherFacts').text(weatherData.currentWeatherFacts);
            $('#airQualityAndUVIntensity').text(weatherData.airQualityAndUVIntensity);
            $('#weatherAndLivingIndex').text(weatherData.weatherAndLivingIndex);

            // 清空未来天气情况表格
            $('#futureWeather tbody').empty();

            // 填充未来天气情况表格
            $.each(weatherData.futureWeather, function(index, forecast) {
                $('#futureWeather tbody').append(
                    $('<tr></tr>').append(
                        // $('<td></td>').text(forecast.date),
                        $('<td></td>').text(forecast.description),
                        $('<td></td>').text(forecast.temperature),
                        $('<td></td>').text(forecast.wind),
                        $('<td></td>').append($('<img>').attr('src', forecast.icon1).attr('alt', '天气图标1')),
                        $('<td></td>').append($('<img>').attr('src', forecast.icon2).attr('alt', '天气图标2'))
                    )
                );
            });
        }
    });
</script>
</body>
</html>
