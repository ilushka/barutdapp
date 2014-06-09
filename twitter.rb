require 'twitter'

client = Twitter::REST::Client.new do |config|
  config.consumer_key    = 'zdS6dZVUIMAgRzkfJo6bk3ssW'
  config.consumer_secret = 'N0UH9BNVXeiRZVDDBNgF943NYkyMvZdBvipfGZ96HSY16wVpTX'
  config.access_token    = '2468510190-JF9YArI3QWeb8UAnsAvAVRnBHnqaHBGF0ozl3MX'
  config.access_token_secret = 'DROcnKeLGCmTVrcsUhBxDPz0Mx8SFC9lKG40bbZXkJp6M'
end

tweet = client.search('from:FCBarUTD', :since_id => (475779287807557632 + 1)).first
puts "timeline: #{tweet.text} #{tweet.id}"
