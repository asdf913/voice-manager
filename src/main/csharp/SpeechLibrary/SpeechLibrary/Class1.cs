using Microsoft.Win32;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Runtime.InteropServices;
using SpeechLib;

namespace SpeechLibrary
{
    public class Class1
     {
 
        [RGiesecke.DllExport.DllExport]
        public static bool isInstalled()
        {
            return System.Type.GetTypeFromProgID("Speech.SpVoice") != null;
        }

        [RGiesecke.DllExport.DllExport]
        public static void speak(IntPtr ptr, int length, String voiceId, int rate, int volume)
        {
            //
            try
            {
                SpVoice tts = new SpVoice();
                //
                tts.Rate = rate;
                //
                tts.Volume = volume;
                //
                tts.Voice = GetVoiceById(tts.GetVoices("", ""), voiceId);
                //
                //ISpeechObjectTokens tokens = tts.GetVoices("", "");
                //
                //if (tokens != null)
                //{
                //
                //Boolean found = false;
                //
                //foreach (SpObjectToken token in tokens)
                // {
                //if (token == null||!Object.Equals(token.Id,voiceId))
                //{
                //continue;
                //}
                //if (!found)
                //{
                //tts.Voice = token;
                //found = true;
                //}
                //else
                //{
                //            throw new ArgumentException();
                //        }
                //    }
                //}
                //
                Console.WriteLine("string =" + ToString(ptr, length));
                //
                Console.WriteLine("voiceId=" + voiceId);
                //
                Console.WriteLine("rate   =" + rate);
                //
                Console.WriteLine("volume =" + volume);
                //
                tts.Speak(ToString(ptr, length), SpeechVoiceSpeakFlags.SVSFDefault
                    );
                //
                tts.WaitUntilDone(System.Threading.Timeout.Infinite);
                //
            }
            catch (Exception e)
            {
                Console.WriteLine(e.HelpLink);
                Console.WriteLine(e.Message);
                Console.WriteLine(e.Source);
                Console.WriteLine(e.StackTrace);
                Console.WriteLine(e.ToString());
                Console.WriteLine(e.InnerException);
                if (e.InnerException != null)
                {
                    Console.WriteLine(e.InnerException.HelpLink);
                    Console.WriteLine(e.InnerException.Message);
                    Console.WriteLine(e.InnerException.Source);
                    Console.WriteLine(e.InnerException.StackTrace);
                    Console.WriteLine(e.InnerException.ToString());
                    Console.WriteLine(e.InnerException.InnerException);
                }
            }
            //
        }


        [RGiesecke.DllExport.DllExport]
        public static void speakXml(IntPtr ptr, int length, String voiceId, int rate, int volume)
        {
            //
            SpVoice tts = new SpVoice();
            //
            tts.Rate = rate;
            //
            tts.Volume = volume;
            //
            tts.Voice = GetVoiceById(tts.GetVoices("", ""), voiceId);
            //
            tts.Speak(ToString(ptr, length), SpeechVoiceSpeakFlags.SVSFIsXML);
            //
        }

        private static String ToString(IntPtr intPtr, int length)
        {
            int[] ints = new int[length];
            Marshal.Copy(intPtr, ints, 0, ints.Length);
            //
            if (ints != null)
            {
                char[] chars = new char[ints.Length];
                for (int i = 0; i < ints.Length; i++)
                {
                    chars[i] = (char)ints[i];
                }
                return new String(chars);
            }
            //
            return null;
            //
        }
        private static SpObjectToken GetVoiceById(ISpeechObjectTokens tokens, String voiceId)
        {
            //
            SpObjectToken result = null;
            //
            if (tokens != null)
            {
                //
                Boolean found = false;
                //
                foreach (SpObjectToken token in tokens)
                {
                    if (token == null || !Object.Equals(token.Id, voiceId))
                    {
                        continue;
                    }
                    if (!found)
                    {
                        result = token;
                        found = true;
                    }
                    else
                    {
                        throw new ArgumentException();
                    }
                }
            }
            //
            return result;
            //
        }

        [RGiesecke.DllExport.DllExport]
        public static void writeVoiceToFile(IntPtr textIntPtr, int textLength, String voiceId, int rate, int volume
            , IntPtr fileNameIntPtr, int fileNameLength)
        {
            //
            SpFileStream sfs = null;
            //
            try
            {
                //
                SpVoice tts = new SpVoice();
                //
                tts.Rate = rate;
                //
                tts.Volume = volume;
                //
                tts.Voice = GetVoiceById(tts.GetVoices("", ""), voiceId);
                //
                //ISpeechObjectTokens tokens = tts.GetVoices("", "");
                //
                //if (tokens != null)
                //{
                //
                //Boolean found = false;
                //
                //foreach (SpObjectToken token in tokens)
                // {
                //if (token == null||!Object.Equals(token.Id,voiceId))
                //{
                //continue;
                //}
                //if (!found)
                //{
                //tts.Voice = token;
                //found = true;
                //}
                //else
                //{
                //            throw new ArgumentException();
                //        }
                //    }
                //}
                //
                (sfs = new SpFileStream()).Open(ToString(fileNameIntPtr, fileNameLength), SpeechStreamFileMode.SSFMCreateForWrite, false);
                //
                tts.AudioOutputStream = sfs;
                //
                tts.Speak(ToString(textIntPtr, textLength), 0);
                //
                tts.WaitUntilDone(System.Threading.Timeout.Infinite);
                //
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
                throw e;
            }
            finally
            {
                if (sfs != null)
                {
                    sfs.Close();
                }
                //
            }
            //
        }


        [RGiesecke.DllExport.DllExport]
        public static String getVoiceAttribute(String voiceId, String attribute)
        {
            //
            SpObjectToken voice = GetVoiceById(new SpVoice().GetVoices("", ""), voiceId);
            //
            return voice != null ? voice.GetAttribute(attribute) : null;
            //
        }

        [RGiesecke.DllExport.DllExport]
        public static String getVoiceIds(String requiredAttributes, String optionalAttributes)
        {
            ISpeechObjectTokens tokens = new SpVoice().GetVoices(requiredAttributes, optionalAttributes);
            //
            List<String> ids = null;
            //
            if (tokens != null)
            {
                foreach (SpObjectToken token in tokens)
                {
                    if (token == null)
                    {
                        continue;
                    }
                    if (ids == null)
                    {
                        ids = new List<String>();
                    }
                    ids.Add(token.Id);
                }
            }
            //
            return ids != null ? String.Join(",", ids) : null;
            //
        }

        [RGiesecke.DllExport.DllExport]
        public static String getProviderName()
        {
            //
            Guid guid = getSpVoiceTypeGuid();
            //
            if (guid != null && !Object.Equals(guid, Guid.Empty))
            {
                return ToString(Registry.GetValue(String.Format(@"HKEY_CLASSES_ROOT\TypeLib\{0}\{1}", Registry.GetValue(String.Format(@"HKEY_CLASSES_ROOT\Interface\{{{0}}}\TypeLib", guid), "", null), Registry.GetValue(String.Format(@"HKEY_CLASSES_ROOT\Interface\{{{0}}}\TypeLib", guid), "Version", null)), "", null));
                //
            }
            //
            return null;
            //
        }

        private static Guid getSpVoiceTypeGuid()
        {
            //
            Guid guid = Guid.Empty;
            //
            foreach (Type et in typeof(SpVoice).Assembly.GetExportedTypes())
            {
                if (et == null)
                {
                    continue;
                }//if
                //
                if (Object.Equals("SpeechLib.SpVoice", et.FullName))
                {
                    if (Object.Equals(guid, Guid.Empty))
                    {
                        guid = et.GUID;
                    }
                    else
                    {
                        throw new Exception();
                    }
                }
            }
            //
            return guid;
            //
        }

        [RGiesecke.DllExport.DllExport]
        public static String getProviderVersion()
        {
            //
            Guid guid = getSpVoiceTypeGuid();
            //
            if (guid != null && !Object.Equals(guid, Guid.Empty))
            {
                Object typeLib = Registry.GetValue(String.Format(@"HKEY_CLASSES_ROOT\Interface\{{{0}}}\TypeLib", guid), "", null);
                Object typeLibVersion = Registry.GetValue(String.Format(@"HKEY_CLASSES_ROOT\Interface\{{{0}}}\TypeLib", guid), "Version", null);
                Object typeLibDll = null;
                String[] platforms = { "win32", "win64" };
                for (int i = 0; i < Int32.MaxValue && typeLibDll == null; i++)
                {
                    foreach (String p in platforms)
                    {
                        if ((typeLibDll = Registry.GetValue(String.Format(@"HKEY_CLASSES_ROOT\TypeLib\{0}\{1}\{2}\{3}", typeLib, typeLibVersion, i, p), "", null)) != null)
                        {
                            break;
                        }
                    }
                }
                if (typeLibDll != null)
                {
                    FileVersionInfo fvi = FileVersionInfo.GetVersionInfo(typeLibDll.ToString());
                    return fvi != null ? fvi.ProductVersion : null;
                }
                //
            }
            //
            return null;
            //
        }

        [RGiesecke.DllExport.DllExport]
        public static String getProviderPlatform()
        {
            //
            Guid guid = getSpVoiceTypeGuid();
            //
            if (guid != null && !Object.Equals(guid, Guid.Empty))
            {
                Object typeLib = Registry.GetValue(String.Format(@"HKEY_CLASSES_ROOT\Interface\{{{0}}}\TypeLib", guid), "", null);
                Object typeLibVersion = Registry.GetValue(String.Format(@"HKEY_CLASSES_ROOT\Interface\{{{0}}}\TypeLib", guid), "Version", null);
                Object typeLibDll = null;
                String[] platforms = { "win32", "win64" };
                String platform = null;
                for (int i = 0; i < Int32.MaxValue && typeLibDll == null; i++)
                {
                    foreach (String p in platforms)
                    {
                        platform = p;
                        //
                        if ((typeLibDll = Registry.GetValue(String.Format(@"HKEY_CLASSES_ROOT\TypeLib\{0}\{1}\{2}\{3}", typeLib, typeLibVersion, i, p), "", null)) != null)
                        {
                            break;
                        }
                    }
                }
                //
                return platform;
                //
            }
            //
            return null;
            //
        }

        private static String ToString(Object instance)
        {
            return instance != null ? instance.ToString() : null;
        }

        private static void GetSpeechApiVersion()
        {
            Guid guid = getSpVoiceTypeGuid();
            //
            if (guid != null && !Object.Equals(guid, Guid.Empty))
            {
                Object typeLib = Registry.GetValue(String.Format(@"HKEY_CLASSES_ROOT\Interface\{{{0}}}\TypeLib", guid), "", null);
                Object typeLibVersion = Registry.GetValue(String.Format(@"HKEY_CLASSES_ROOT\Interface\{{{0}}}\TypeLib", guid), "Version", null);
                Object typeLibName = Registry.GetValue(String.Format(@"HKEY_CLASSES_ROOT\TypeLib\{0}\{1}", typeLib, typeLibVersion), "", null);
                Object typeLibDll = null;
                String[] platforms = { "win32", "win64" };
                String platform = null;
                for (int i = 0; i < Int32.MaxValue && typeLibDll == null; i++)
                {
                    foreach (String p in platforms)
                    {
                        if ((typeLibDll = Registry.GetValue(String.Format(@"HKEY_CLASSES_ROOT\TypeLib\{0}\{1}\{2}\{3}", typeLib, typeLibVersion, i, platform = p), "", null)) != null)
                        {
                            break;
                        }
                    }
                }
                if (typeLibDll != null)
                {
                    FileVersionInfo fvi = FileVersionInfo.GetVersionInfo(typeLibDll.ToString());
                    Console.WriteLine(fvi != null ? fvi.ProductVersion : null);
                }
                //
            }
        }

    }

}