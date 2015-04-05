using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Globalization;
using System.IO;
using System.Linq;
using System.Threading;

internal static class Polynomial_Regression_Office_Prices {
	private static void Main(String[] args) {
		var inputData = ReadInput();
		var TrainingData = inputData.Item1.Select(polinomialForm).ToArray();
		var TrainingCost = inputData.Item2;
		var TestData = inputData.Item3.Select(polinomialForm).ToArray();
		double[] minTheta = GradientDescent(TrainingData, TrainingCost).Item1;
		TestData.Select(sample => H(sample, minTheta)).ToList().ForEach(Console.WriteLine);
	}

	private static List<IEnumerable<int>> cmb;
	
	private static double[] polinomialForm(double[] sample) {
		List<double> ret = new List<double>();
		ret.Add(1);
		
		foreach (var term in cmb) {
			double t = 1;
			
			foreach (var i in term) {
				t *= sample[i];
			}
			
			ret.Add(t);
		}
		
		return ret.ToArray();
	}

	public static IEnumerable<IEnumerable<T>> Combinations<T>(this IEnumerable<T> elements, int k) {
		return k == 0 ? new[] {new T[0]} :
			elements.SelectMany((e, i) => elements
			.Skip(i)
			.Combinations(k - 1)
			.Select(c => (new[] {e}).Concat(c)));
	}

	private static Tuple<double[][], double[], double[][]> ReadInput() {
		Thread.CurrentThread.CurrentCulture = CultureInfo.InvariantCulture;
		Random r = new Random(222);
		List<int> randomize;
		double[][] trainingData;
		double[] cost;
		List<double[]> testData = new List<double[]>();
		var reader = Console.In;

		{
			var line = reader.ReadLine().Split().Select(int.Parse);
			int F = line.First();
			int N = line.Last();
			trainingData = new double[N][];
			cost = new double[N];
			randomize = Enumerable.Range(0, N).ToList();
			randomize.Sort((a, b) => (r.Next()%3) - 1);
			
			for (int i = 0; i < N; i++) {
				var values =
						reader.ReadLine()
						.Split(" ".ToCharArray(), StringSplitOptions.RemoveEmptyEntries)
						.Select(double.Parse)
						.ToArray();
				trainingData[randomize[i]] = values.Take(F).ToArray();
				cost[randomize[i]] = values.Last();
			}
			
			int T = reader.ReadLine().Split().Select(int.Parse).First();
			
			for (int i = 0; i < T; i++) {
				var values = reader.ReadLine().Split().Select(double.Parse).ToArray();
				testData.Add(values);
			}
			
			cmb = new List<IEnumerable<int>>();
			
			for (int i = 1; i < 4; i++) {
				cmb.AddRange(Enumerable.Range(0, F).Combinations(i));
			}
		}
		
		return new Tuple<double[][], double[], double[][]>(trainingData.ToArray(), cost.ToArray(), testData.ToArray());
	}
	
	private static Tuple<double[],double> GradientDescent(double[][] trainingData, double[] trainingCost) {
		double[] theta = new double[trainingData[0].Length];
		double[] tempTheta = new double[trainingData[0].Length];
		double alpha = 0.1;
		double oldCost;
		double newCost = double.MaxValue;
		int trainSetCount = trainingData.Length;
		List<double> costPlot = new List<double>();
		
		do {
			for (int i = 0; i < trainSetCount; i++) {
				double[] x = trainingData[i];
				double y = trainingCost[i];
				
				for (int j = 0; j < theta.Length; j++) {
					tempTheta[j] -= alpha*(H(x, theta) - y)*x[j]/trainSetCount;
				}
			}
			
			theta = tempTheta.ToArray();
			oldCost = newCost;
			newCost = Cost(trainingData, trainingCost, theta);
			costPlot.Add(newCost);
		} while (Math.Abs(oldCost - newCost) > 0.1);

		return Tuple.Create(theta, newCost);
	}

	private static double H(double[] x, double[] theta) {
		double z = 0;
		
		for (int i = 0; i < x.Length; i++) {
			z += x[i]*theta[i];
		}
		
		return z;
	}

	private static double Cost(double[][] x, double[] y, double[] theta) {
		double sum = 0;
		
		for (int i = 0; i < x.Length; i++) {
			sum += Math.Pow(H(x[i], theta) - y[i], 2)/(2*x.Length);
		}
		
		return sum;
	}
}
