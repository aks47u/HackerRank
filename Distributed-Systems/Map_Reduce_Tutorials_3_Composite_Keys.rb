require 'json'

class MapReduce
  def initialize
    @intermediate = Hash.new{|x, y| x[y] = Array.new}
    @finalResult = []
  end

  def emitIntermediate(key, value)
    @intermediate[key] += [value]
  end

  def emit(value)
    @finalResult += [value]
  end

  def execute(data, mapper, reducer)
    data.each do |line|
      warn "Loading data:#{line}"
      record = JSON.parse(line)
      mapper(self, record)
    end

    @intermediate.keys.each do |key|
      reducer(self, key, @intermediate[key])
    end

    @finalResult.each do |item|
      outputMap = Hash.new
      outputMap["key"] = item.key
      outputMap["value"] = item.value
      puts outputMap.to_json
    end
  end
end

class Pair
  attr_accessor :key, :value
  def initialize(key, value)
    @key = key
    @value = value
  end
end

def mapper(mapred, record)
  country_and_state = record["key"]
  population = record["value"]
  mapred.emitIntermediate(country_and_state, population.to_f)
end

def reducer(mapred, key, list_of_values)
  population = list_of_values.inject(:+).round
  resultPair = Pair.new(key, population)
  mapred.emit(resultPair)
end

inputData = []

while line = gets()
  country, state, city, population = line.strip.split("\t")
  inputData += [{"key" => "#{country},#{state}", "value" => population}.to_json]
end

@mapred = MapReduce.new()
@mapred.execute(inputData, method(:mapper), method(:reducer))
