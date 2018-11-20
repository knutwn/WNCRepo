using System;
using System.Collections.Generic;

namespace splitData
{
    class MainClass
    {
        private static bool IsTime(String data)
        {
            bool result = false;
            int colonPos = data.IndexOf(":");
            int dotPos = data.IndexOf(".");
            if (colonPos > 0 && dotPos == -1)
            {
                String[] timePart = data.Split(':');
                if (timePart.Length == 3)
                {
                    foreach (var ch in timePart)
                    {
                        result = int.TryParse(ch, out int n);
                    }
                }
            }
            else if (colonPos == -1 && dotPos > 0)
            {
                String[] timePart = data.Split('.');
                if (timePart.Length == 3)
                {
                    foreach (var ch in timePart)
                    {
                        result = int.TryParse(ch, out int n);
                    }
                }
            }
            else if (colonPos != -1 && dotPos != -1)
            {
                String[] timePart = data.Split(':');
                foreach (var tp in timePart)
                {
                    result = int.TryParse(tp, out int n);
                    if (result == false)
                    {
                        string[] dotPart = tp.Split('.');
                        foreach (var dp in dotPart)
                        {
                            result = int.TryParse(dp, out int t);
                        }
                    }
                }
            }
            return result;
        }

        private static bool IsClass(String data)
        {
            bool result = false;
            int dashPos = data.IndexOf("-");
            if (dashPos > 1)
            {
                if (int.TryParse(data[dashPos - 1].ToString(), out int n) && int.TryParse(data[dashPos + 1].ToString(), out int r))
                {
                    result = true;
                }
            }
            return result;
        }

        private static bool IsName(String data, String[] manArray, String[] womanArray, String[] lastNameArray)
        {
            bool result = false;
            foreach (String man in manArray)
            {
                if (data.Equals(man))
                {
                    result = true;
                }
            }

            foreach (String woman in womanArray)
            {
                if (data.Equals(woman))
                {
                    result = true;
                }
            }

            foreach (String lastName in lastNameArray)
            {
                if (data.Equals(lastName))
                {
                    result = true;
                }
            }

            return result;
        }
        private static String FindFormat(String data, String[] manArray, String[] womanArray, String[] lastNameArray)
        {
            String format = "";
            if (int.TryParse(data, out int result))
            {
                format = "N";
            }
            else if (IsTime(data))
            {
                format = "TIME";
            }
            else if (IsClass(data))
            {
                format = "CLASS";
            }
            else if (IsName(data, manArray, womanArray, lastNameArray))
            {
                format = "NAME";
            }
            else
            {
                format = "TEXT";
            }
            return format;
        }


        public static void Main(string[] args)
        {

            String[] inputDataFromFile = { };
            String[] configData = { };
            var configMap = new Dictionary<string, string>();

            if (args.Length > 1)
            {
                inputDataFromFile = System.IO.File.ReadAllLines(args[0]);
                configData = System.IO.File.ReadAllLines(args[1]);
            }
            else
            {
                System.Console.WriteLine("no Input and config file spesified");
            }

            foreach (var config in configData)
            {
                String[] configLine = config.Split('=');
                configMap.Add(configLine[0], configLine[1]);
            }

            configMap.TryGetValue("manData", out string manDataFile);
            configMap.TryGetValue("womanData", out string womanDataFile);
            configMap.TryGetValue("lastNameData", out string lastNameDataFile);

            String[] manData = System.IO.File.ReadAllLines(manDataFile);
            String[] womanData = System.IO.File.ReadAllLines(womanDataFile);
            String[] lastNameData = System.IO.File.ReadAllLines(lastNameDataFile);

            foreach (String inputData in inputDataFromFile)
            {

                var formatMap = new Dictionary<int, string>();
                var colWidthMap = new Dictionary<int, int>();

                string[] splitString = inputData.Split(' ');

                for (int i = 0; i < splitString.Length; i++)
                {
                    String format = FindFormat(splitString[i], manData, womanData, lastNameData);
                    formatMap.Add(i, format);
                    colWidthMap.Add(i, splitString[i].Length);
                    //System.Console.WriteLine($"format for {splitString[i]} = {format}");
                }

                String currFormat;
                String prevFormat = "";

                foreach (var formatPair in formatMap)
                {
                    currFormat = formatPair.Value;
                    if (currFormat.Equals(prevFormat))
                    {
                        System.Console.Write($"{splitString[formatPair.Key]} ");
                    }
                    else
                    {
                        System.Console.Write($"|{splitString[formatPair.Key]} ");
                    }

                    prevFormat = currFormat;
                }
                System.Console.WriteLine();
            }
            System.Console.ReadKey();
        }
    }
}
