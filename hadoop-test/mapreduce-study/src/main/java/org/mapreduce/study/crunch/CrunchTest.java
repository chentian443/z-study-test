package org.mapreduce.study.crunch;

import java.util.Set;

import org.apache.crunch.DoFn;
import org.apache.crunch.Emitter;
import org.apache.crunch.FilterFn;
import org.apache.crunch.PCollection;
import org.apache.crunch.PTable;
import org.apache.crunch.Pipeline;
import org.apache.crunch.PipelineResult;
import org.apache.crunch.impl.mr.MRPipeline;
import org.apache.crunch.scrunch.To;
import org.apache.crunch.scrunch.Writables;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;

public class CrunchTest extends Configured implements Tool {
	public static void main(String[] args) throws Exception {
		//String a[] = new String[] { "/zhonghui/input", "/zhonghui/output" };
		String a[] = new String[] { "D:/BaiduNetdiskDownload/mapreduce_test/crunchTest/input",
				"D:/BaiduNetdiskDownload/mapreduce_test/crunchTest/output" };
		Configuration conf = new Configuration();
		ToolRunner.run(conf, new CrunchTest(), a);
	}

	@Override
	public int run(String[] args) throws Exception {
		String inputPath = args[0];
		String outPath = args[1];
		// 创建一个crunch管线，mapreduce管线（也可以是SparkPipeline）
		Pipeline pipeline = new MRPipeline(CrunchTest.class, getConf());
		// crunch特有的数据集：PCollection
		PCollection<String> lines = pipeline.readTextFile(inputPath);
		// 
		PCollection<String> words = lines.parallelDo(new Tokenizer(), Writables.strings());

		PCollection<String> noStopWords = words.filter(new StopWordFilter());
		// 聚合方法 count(),还有其他如，groupByKey(),combineValues()
		PTable<String, Long> counts = noStopWords.count();
		// 或者 counts.write(To.textFile(outPath));
		pipeline.writeTextFile(counts, outPath);
		// 
		PipelineResult result = pipeline.done();
		return result.succeeded() ? 0 : 1;
	}

}

class StopWordFilter extends FilterFn<String> {
	// English stop words, borrowed from Lucene.
	private static final Set<String> STOP_WORDS = ImmutableSet.copyOf(new String[] { "a", "and", "are", "as", "at",
			"be", "but", "by", "for", "if", "in", "into", "is", "it", "no", "not", "of", "on", "or", "s", "such", "t",
			"that", "the", "their", "then", "there", "these", "they", "this", "to", "was", "will", "with" });

	@Override
	public boolean accept(String word) {
		return !STOP_WORDS.contains(word);
	}
}

class Tokenizer extends DoFn<String, String> {
	private static final long serialVersionUID = 1L;
	private static final Splitter SPLITTER = Splitter.onPattern("\\s+").omitEmptyStrings();

	@Override
	public void process(String line, Emitter<String> emitter) {
		for (String word : SPLITTER.split(line)) {
			emitter.emit(word);
		}
	}
}
