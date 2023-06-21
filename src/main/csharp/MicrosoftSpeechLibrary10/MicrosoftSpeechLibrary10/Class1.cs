using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Runtime.InteropServices;
using System.Speech.Synthesis;

namespace MicrosoftSpeechLibrary10
{
    public class Class1
    {

        static Class1()
        {
            Console.WriteLine("Location=" + Assembly.GetAssembly(typeof(SpeechSynthesizer)).Location);
        }

        [RGiesecke.DllExport.DllExport]
        public static void speak(IntPtr ptr, int length, String voiceId, int rate, int volume)
        {
            //
            try
            {
                //
                SpeechSynthesizer speechSynthesizer = new SpeechSynthesizer();
                //
                speechSynthesizer.SetOutputToDefaultAudioDevice();
                //
                speechSynthesizer.Rate = rate;
                //
                speechSynthesizer.Volume = volume;
                //
                speechSynthesizer.SelectVoice(getVoiceAttribute(speechSynthesizer.GetInstalledVoices(), voiceId, "Name"));
                //
                speechSynthesizer.Speak(ToString(ptr, length));
                //
            }catch(Exception e)
            {
                Console.WriteLine(e.ToString());
            }
            //
        }

        [RGiesecke.DllExport.DllExport]
        public static void speakSsml(IntPtr ptr, int length, String voiceId, int rate, int volume)
        {
            //
            try
            {
                //
                SpeechSynthesizer speechSynthesizer = new SpeechSynthesizer();
                //
                speechSynthesizer.SetOutputToDefaultAudioDevice();
                //
                speechSynthesizer.Rate = rate;
                //
                speechSynthesizer.Volume = volume;
                //
                speechSynthesizer.SelectVoice(getVoiceAttribute(speechSynthesizer.GetInstalledVoices(), voiceId, "Name"));
                //
                speechSynthesizer.SpeakSsml(ToString(ptr, length));
                //
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
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

        private static InstalledVoice GetInstalledVoice(IEnumerable<InstalledVoice> installedVoices,String voiceId)
        {
            //
            InstalledVoice result = null;
            //
            if (installedVoices != null)
            {
                //
                Boolean found = false;
                //
                foreach (InstalledVoice installedVoice in installedVoices)
                {
                    if (installedVoice == null|| installedVoice.VoiceInfo==null || !Object.Equals(installedVoice.VoiceInfo.Id, voiceId))
                    {
                        continue;
                    }
                    if (!found)
                    {
                        result = installedVoice;
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
            SpeechSynthesizer speechSynthesizer = null;
            //
            try
            {
                //
                (speechSynthesizer = new SpeechSynthesizer()).SetOutputToWaveFile(ToString(fileNameIntPtr, fileNameLength));
                //
                speechSynthesizer.Rate = rate;
                //
                speechSynthesizer.Volume = volume;
                //
                speechSynthesizer.SelectVoice(getVoiceAttribute(speechSynthesizer.GetInstalledVoices(), voiceId, "Name"));
                //
                speechSynthesizer.Speak(ToString(textIntPtr, textLength));
                //
            }finally {
                if(speechSynthesizer!= null)
                {
                    speechSynthesizer.Dispose();
                }
            }
            //
        }

        [RGiesecke.DllExport.DllExport]
        public static String getVoiceAttribute(String voiceId, String attribute)
        {
            //
            try
            {
                //
                InstalledVoice installedVoice = GetInstalledVoice(new SpeechSynthesizer().GetInstalledVoices(), voiceId);
                //
                if (installedVoice != null && installedVoice.VoiceInfo != null)
                {
                    PropertyInfo pi = installedVoice.VoiceInfo.GetType().GetProperty(attribute);
                    //
                    if (pi != null){
                        return ToString(pi.GetValue(installedVoice.VoiceInfo, null));
                    }else if (installedVoice.VoiceInfo.AdditionalInfo.Keys.Contains(attribute)){
                        return installedVoice.VoiceInfo.AdditionalInfo[attribute];
                    }
                }
            } catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
            //
            return null;
            //
        }

         static String getVoiceAttribute(IEnumerable<InstalledVoice> installedVoices,String voiceId, String attribute)
        {
            //
            try
            {
                //
                InstalledVoice installedVoice = GetInstalledVoice(installedVoices, voiceId);
                //
                if (installedVoice != null && installedVoice.VoiceInfo != null)
                {
                    PropertyInfo pi = installedVoice.VoiceInfo.GetType().GetProperty(attribute);
                    //
                    if (pi != null)
                    {
                        return ToString(pi.GetValue(installedVoice.VoiceInfo, null));
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
            //
            return null;
            //
        }

        [RGiesecke.DllExport.DllExport]
        public static IntPtr getVoiceIds(out int length)
        {
            //
            string[] ss = new SpeechSynthesizer().GetInstalledVoices().Select(x => x != null && x.VoiceInfo != null ? x.VoiceInfo.Id : null).ToArray();
            //
            length = ss != null ? ss.Length : 0;
            //
            return ConvertStringArrayToIntPtr(ss);
            //
        }

        private static IntPtr ConvertStringArrayToIntPtr(string[] array)
        {
            IntPtr[] stringPointers = new IntPtr[array.Length];

            for (int i = 0; i < array.Length; i++)
            {
                stringPointers[i] = Marshal.StringToHGlobalUni(array[i]);
            }

            IntPtr arrayPtr = Marshal.AllocHGlobal(IntPtr.Size * array.Length);
            Marshal.Copy(stringPointers, 0, arrayPtr, array.Length);

            return arrayPtr;
        }

        [RGiesecke.DllExport.DllExport]
        public static String getDllPath()
        {
            //
            return Assembly.GetAssembly(typeof(SpeechSynthesizer)).Location;
            //
        }

        [RGiesecke.DllExport.DllExport]
        public static String getProviderPlatform()
        {
            //
            bool is32Bit = false;
            bool is64Bit = false;
            try
            {
                //PeNet.PeFile peFile = new PeNet.PeFile(typeof(SpeechSynthesizer).Assembly.Location);
                //
                //is32Bit = peFile.Is32Bit;
                //is64Bit = peFile.Is64Bit;
                //
            } catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
            //
            if (is32Bit != is64Bit)
            {
                return is32Bit ? "32" : "64";
            }
            //
            return "Error";
            //
        }

        private static String ToString(Object instance)
        {
            return instance != null ? instance.ToString() : null;
        }

    }

}
