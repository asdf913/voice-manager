using System;
using System.Collections.Generic;
using System.Text;
using Windows.Foundation.Metadata;
using Windows.Globalization;
using Windows.Globalization;
using System.Threading;
using System;
using System.Collections.Generic;
using System.Text;
using Windows.Data.Json;

namespace JapanesePhonemeLibrary
{
    public class JapanesePhonemeLibrary
    {
        public static String GetWordsAsJson()
        {
            IEnumerator<JapanesePhoneme> words =
            JapanesePhoneticAnalyzer.GetWords("体調不良で国歌演奏セレモニーに遅刻したサインツに多額の罰金").GetEnumerator();
            JsonArray jsonArray = null;
            JsonObject jsonObject = null;
            while (words != null && words.MoveNext())
            {
                jsonObject = new JsonObject();
                jsonObject.Add("DisplayText", JsonValue.CreateStringValue(words.Current.DisplayText));
                jsonObject.Add("IsPhraseStart", JsonValue.CreateBooleanValue(words.Current.IsPhraseStart));
                jsonObject.Add("YomiText", JsonValue.CreateStringValue(words.Current.YomiText));
                if (jsonArray == null)
                {
                    jsonArray = new JsonArray();
                }
                jsonArray.Add(jsonObject);
            }
            return jsonArray!=null?jsonArray.ToString():null;

        }
    }
}
