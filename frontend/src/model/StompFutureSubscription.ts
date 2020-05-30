import { IMessage } from '@stomp/stompjs';

export default class StompFutureSubscription {
  destination!: string;

  callback!: (message: IMessage) => void
}
