package com.arya199.gemstone.data.source.remote

import com.arya199.gemstone.api.response.LiveResponse
import com.arya199.gemstone.data.Currency
import com.arya199.gemstone.data.Rate
import com.arya199.gemstone.data.Result
import com.arya199.gemstone.data.source.CurrencyLayerDataSource
import com.google.gson.Gson
import javax.inject.Inject

// Since CurrencyLayer free plan only limits to 250 API calls per month, this fake class is created
// to bypass that limitation.
class FakeRemoteDataSource @Inject constructor(): CurrencyLayerDataSource {

    override suspend fun getLiveRate(): Result<List<Rate>> {
        /**
         * Example json
         * {"success":true,"terms":"https:\/\/currencylayer.com\/terms","privacy":"https:\/\/currencylayer.com\/privacy","timestamp":1603298405,"source":"USD","quotes":{"USDAED":3.672969,"USDAFN":76.899195,"USDALL":105.025024,"USDAMD":494.369489,"USDANG":1.794864,"USDAOA":654.119003,"USDARS":77.712401,"USDAUD":1.402338,"USDAWG":1.8,"USDAZN":1.704736,"USDBAM":1.649105,"USDBBD":2.01898,"USDBDT":84.789312,"USDBGN":1.648732,"USDBHD":0.376669,"USDBIF":1935,"USDBMD":1,"USDBND":1.355222,"USDBOB":6.909557,"USDBRL":5.592401,"USDBSD":0.99992,"USDBTC":7.7677272e-5,"USDBTN":73.691542,"USDBWP":11.408311,"USDBYN":2.545605,"USDBYR":19600,"USDBZD":2.015523,"USDCAD":1.311775,"USDCDF":1962.999406,"USDCHF":0.904055,"USDCLF":0.028355,"USDCLP":782.299718,"USDCNY":6.651102,"USDCOP":3768.38,"USDCRC":602.852457,"USDCUC":1,"USDCUP":26.5,"USDCVE":93.199838,"USDCZK":22.926598,"USDDJF":178.010869,"USDDKK":6.26723,"USDDOP":58.449557,"USDDZD":128.569849,"USDEGP":15.701602,"USDERN":14.999946,"USDETB":37.086468,"USDEUR":0.842115,"USDFJD":2.141004,"USDFKP":0.759505,"USDGBP":0.759555,"USDGEL":3.235012,"USDGGP":0.759505,"USDGHS":5.81498,"USDGIP":0.759505,"USDGMD":51.749968,"USDGNF":9750.000115,"USDGTQ":7.779459,"USDGYD":209.183007,"USDHKD":7.74995,"USDHNL":24.501218,"USDHRK":6.377202,"USDHTG":62.415946,"USDHUF":306.383977,"USDIDR":14646.2,"USDILS":3.37475,"USDIMP":0.759505,"USDINR":73.64825,"USDIQD":1190,"USDIRR":42104.999766,"USDISK":138.359954,"USDJEP":0.759505,"USDJMD":145.455082,"USDJOD":0.709012,"USDJPY":104.4795,"USDKES":108.710144,"USDKGS":80.800479,"USDKHR":4105.000077,"USDKMF":414.67495,"USDKPW":899.994264,"USDKRW":1132.140365,"USDKWD":0.30555,"USDKYD":0.833309,"USDKZT":427.854824,"USDLAK":9250.000234,"USDLBP":1507.101902,"USDLKR":184.235986,"USDLRD":191.898187,"USDLSL":16.539994,"USDLTL":2.95274,"USDLVL":0.60489,"USDLYD":1.370051,"USDMAD":9.145495,"USDMDL":17.046664,"USDMGA":3925.000302,"USDMKD":51.945369,"USDMMK":1293.401883,"USDMNT":2837.669105,"USDMOP":7.98167,"USDMRO":357.000515,"USDMUR":40.098339,"USDMVR":15.40981,"USDMWK":754.999596,"USDMXN":21.0613,"USDMYR":4.1435,"USDMZN":72.899577,"USDNAD":16.540448,"USDNGN":381.502218,"USDNIO":34.730265,"USDNOK":9.19936,"USDNPR":117.905068,"USDNZD":1.49875,"USDOMR":0.384985,"USDPAB":0.99992,"USDPEN":3.59875,"USDPGK":3.502499,"USDPHP":48.47702,"USDPKR":162.25044,"USDPLN":3.849902,"USDPYG":7027.011075,"USDQAR":3.640993,"USDRON":4.104101,"USDRSD":99.039708,"USDRUB":76.720394,"USDRWF":975,"USDSAR":3.750369,"USDSBD":8.08084,"USDSCR":18.710644,"USDSDG":55.250377,"USDSEK":8.71573,"USDSGD":1.35355,"USDSHP":0.759505,"USDSLL":9924.999957,"USDSOS":582.999838,"USDSRD":14.154045,"USDSTD":21031.906016,"USDSVC":8.749615,"USDSYP":512.084234,"USDSZL":16.539742,"USDTHB":31.180106,"USDTJS":10.317919,"USDTMT":3.51,"USDTND":2.744499,"USDTOP":2.322403,"USDTRY":7.798798,"USDTTD":6.782575,"USDTWD":28.6795,"USDTZS":2319.999822,"USDUAH":28.255614,"USDUGX":3741.752706,"USDUSD":1,"USDUYU":42.796976,"USDUZS":10363.999768,"USDVEF":9.987504,"USDVND":23178,"USDVUV":114.486528,"USDWST":2.62653,"USDXAF":553.081142,"USDXAG":0.039797,"USDXAU":0.000519,"USDXCD":2.70255,"USDXDR":0.706236,"USDXOF":558.000315,"USDXPF":100.874997,"USDYER":250.302645,"USDZAR":16.280602,"USDZMK":9001.219847,"USDZMW":20.27361,"USDZWL":321.999941}}
         */
        val jsonExample = "{\"success\":true,\"terms\":\"https:\\/\\/currencylayer.com\\/terms\",\"privacy\":\"https:\\/\\/currencylayer.com\\/privacy\",\"timestamp\":1603298405,\"source\":\"USD\",\"quotes\":{\"USDAED\":3.672969,\"USDAFN\":76.899195,\"USDALL\":105.025024,\"USDAMD\":494.369489,\"USDANG\":1.794864,\"USDAOA\":654.119003,\"USDARS\":77.712401,\"USDAUD\":1.402338,\"USDAWG\":1.8,\"USDAZN\":1.704736,\"USDBAM\":1.649105,\"USDBBD\":2.01898,\"USDBDT\":84.789312,\"USDBGN\":1.648732,\"USDBHD\":0.376669,\"USDBIF\":1935,\"USDBMD\":1,\"USDBND\":1.355222,\"USDBOB\":6.909557,\"USDBRL\":5.592401,\"USDBSD\":0.99992,\"USDBTC\":7.7677272e-5,\"USDBTN\":73.691542,\"USDBWP\":11.408311,\"USDBYN\":2.545605,\"USDBYR\":19600,\"USDBZD\":2.015523,\"USDCAD\":1.311775,\"USDCDF\":1962.999406,\"USDCHF\":0.904055,\"USDCLF\":0.028355,\"USDCLP\":782.299718,\"USDCNY\":6.651102,\"USDCOP\":3768.38,\"USDCRC\":602.852457,\"USDCUC\":1,\"USDCUP\":26.5,\"USDCVE\":93.199838,\"USDCZK\":22.926598,\"USDDJF\":178.010869,\"USDDKK\":6.26723,\"USDDOP\":58.449557,\"USDDZD\":128.569849,\"USDEGP\":15.701602,\"USDERN\":14.999946,\"USDETB\":37.086468,\"USDEUR\":0.842115,\"USDFJD\":2.141004,\"USDFKP\":0.759505,\"USDGBP\":0.759555,\"USDGEL\":3.235012,\"USDGGP\":0.759505,\"USDGHS\":5.81498,\"USDGIP\":0.759505,\"USDGMD\":51.749968,\"USDGNF\":9750.000115,\"USDGTQ\":7.779459,\"USDGYD\":209.183007,\"USDHKD\":7.74995,\"USDHNL\":24.501218,\"USDHRK\":6.377202,\"USDHTG\":62.415946,\"USDHUF\":306.383977,\"USDIDR\":14646.2,\"USDILS\":3.37475,\"USDIMP\":0.759505,\"USDINR\":73.64825,\"USDIQD\":1190,\"USDIRR\":42104.999766,\"USDISK\":138.359954,\"USDJEP\":0.759505,\"USDJMD\":145.455082,\"USDJOD\":0.709012,\"USDJPY\":104.4795,\"USDKES\":108.710144,\"USDKGS\":80.800479,\"USDKHR\":4105.000077,\"USDKMF\":414.67495,\"USDKPW\":899.994264,\"USDKRW\":1132.140365,\"USDKWD\":0.30555,\"USDKYD\":0.833309,\"USDKZT\":427.854824,\"USDLAK\":9250.000234,\"USDLBP\":1507.101902,\"USDLKR\":184.235986,\"USDLRD\":191.898187,\"USDLSL\":16.539994,\"USDLTL\":2.95274,\"USDLVL\":0.60489,\"USDLYD\":1.370051,\"USDMAD\":9.145495,\"USDMDL\":17.046664,\"USDMGA\":3925.000302,\"USDMKD\":51.945369,\"USDMMK\":1293.401883,\"USDMNT\":2837.669105,\"USDMOP\":7.98167,\"USDMRO\":357.000515,\"USDMUR\":40.098339,\"USDMVR\":15.40981,\"USDMWK\":754.999596,\"USDMXN\":21.0613,\"USDMYR\":4.1435,\"USDMZN\":72.899577,\"USDNAD\":16.540448,\"USDNGN\":381.502218,\"USDNIO\":34.730265,\"USDNOK\":9.19936,\"USDNPR\":117.905068,\"USDNZD\":1.49875,\"USDOMR\":0.384985,\"USDPAB\":0.99992,\"USDPEN\":3.59875,\"USDPGK\":3.502499,\"USDPHP\":48.47702,\"USDPKR\":162.25044,\"USDPLN\":3.849902,\"USDPYG\":7027.011075,\"USDQAR\":3.640993,\"USDRON\":4.104101,\"USDRSD\":99.039708,\"USDRUB\":76.720394,\"USDRWF\":975,\"USDSAR\":3.750369,\"USDSBD\":8.08084,\"USDSCR\":18.710644,\"USDSDG\":55.250377,\"USDSEK\":8.71573,\"USDSGD\":1.35355,\"USDSHP\":0.759505,\"USDSLL\":9924.999957,\"USDSOS\":582.999838,\"USDSRD\":14.154045,\"USDSTD\":21031.906016,\"USDSVC\":8.749615,\"USDSYP\":512.084234,\"USDSZL\":16.539742,\"USDTHB\":31.180106,\"USDTJS\":10.317919,\"USDTMT\":3.51,\"USDTND\":2.744499,\"USDTOP\":2.322403,\"USDTRY\":7.798798,\"USDTTD\":6.782575,\"USDTWD\":28.6795,\"USDTZS\":2319.999822,\"USDUAH\":28.255614,\"USDUGX\":3741.752706,\"USDUSD\":1,\"USDUYU\":42.796976,\"USDUZS\":10363.999768,\"USDVEF\":9.987504,\"USDVND\":23178,\"USDVUV\":114.486528,\"USDWST\":2.62653,\"USDXAF\":553.081142,\"USDXAG\":0.039797,\"USDXAU\":0.000519,\"USDXCD\":2.70255,\"USDXDR\":0.706236,\"USDXOF\":558.000315,\"USDXPF\":100.874997,\"USDYER\":250.302645,\"USDZAR\":16.280602,\"USDZMK\":9001.219847,\"USDZMW\":20.27361,\"USDZWL\":321.999941}}"
        val convertedJson = Gson().fromJson<LiveResponse>(jsonExample, LiveResponse::class.java)
        val rateToReturn = ArrayList<Rate>()
        val allQuotePairs = convertedJson.quotes.keys
        for (key in allQuotePairs) {
            if (key.length != 6) continue
            val from = key.substring(0, 3)
            val end = key.substring(3, key.length)
            rateToReturn.add(Rate(from = from, to = end, rate = convertedJson.quotes[key]))
        }
        return Result.Success(rateToReturn)
        /**
         * Example faulty Json.
         */
//        val jsonExample = "{\"success\": false,\"error\": {\"code\": 104,\"info\": " +
//                "\"Your monthly usage limit has been reached. Please upgrade your subscription plan.}}"
//        val convertedJson = Gson().fromJson<LiveResponse>(jsonExample, LiveResponse::class.java)
//        return Result.Error()
    }

    override suspend fun getCurrencyList(): Result<List<Currency>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveCurrency(currency: Currency) {
        TODO("Not yet implemented")
    }

    override suspend fun saveRate(rate: Rate) {
        TODO("Not yet implemented")
    }
}